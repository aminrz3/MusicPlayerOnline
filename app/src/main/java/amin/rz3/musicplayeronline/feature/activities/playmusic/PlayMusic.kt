package amin.rz3.musicplayeronline.feature.activities.playmusic

import amin.rz3.musicplayeronline.BuildConfig
import amin.rz3.musicplayeronline.R
import amin.rz3.musicplayeronline.common.convertDpToPixel
import amin.rz3.musicplayeronline.common.convertMillisToString
import amin.rz3.musicplayeronline.data.Music
import amin.rz3.musicplayeronline.services.ImageLoadingService
import android.Manifest
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.util.DisplayMetrics
import android.view.View
import androidx.core.app.ActivityCompat
import com.google.android.material.slider.Slider
import kotlinx.android.synthetic.main.activity_play_music.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber
import java.io.File
import java.util.*

class PlayMusic : AppCompatActivity() {
    val playMusicViewModel: PlayMusicViewModel by viewModel { parametersOf(intent.extras) }
    val imageLoadingService: ImageLoadingService by inject()
    private var music: Music? = null
    private var mediaPlayer: MediaPlayer? = null
    private var isDragging: Boolean = false
    private var timer: Timer? = null
    private var downloadManager: DownloadManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_music)

        val audioManager: AudioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        playMusicViewModel.musicLiveData.observe(this) {
            music = it
            val title = "${music?.Singer} - ${music?.name}"
            if (checkDownloaded(title)) img_download.visibility = View.GONE
            imageLoadingService.load(cover_music, it.image_url)
            tv_name_music.text = it.name
            tv_singer_music.text = it.Singer
            tv_time_music.text = it.time
        }

        img_back_playMusic.setOnClickListener {
            finish()
        }

        img_download.setOnClickListener {
            music?.let {
                val title = "${it.Singer} - ${it.name}"
                download(title, null, it.music_url)
            }
        }

        img_volum_up.setOnClickListener {
            audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND)
        }

        img_volum_down.setOnClickListener {
            audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND)
        }

        slider.addOnChangeListener(Slider.OnChangeListener { slider, value, fromUser ->
            tv_time_playing.text = convertMillisToString(value.toLong())
        })

        slider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
                isDragging = true
            }

            override fun onStopTrackingTouch(slider: Slider) {
                isDragging = false
                if (mediaPlayer != null) {
                    mediaPlayer?.seekTo(slider.value.toInt())
                }
            }
        })

        btn_play_music.setOnClickListener {
            if (music != null) {
                if (mediaPlayer == null) {
                    btn_play_music.isClickable = false
                    mediaPlayer = MediaPlayer()
                    mediaPlayer?.setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build()
                    )

                    val title = "${music?.Singer} - ${music?.name}"
                    if (checkDownloaded(title)) {
                        mediaPlayer?.setDataSource(this, Uri.fromFile(getMusicFile(title)))
                    } else {
                        PlayProgress.show()
                        mediaPlayer?.setDataSource(this, Uri.parse(music?.music_url))
                    }

                    mediaPlayer?.prepareAsync()
                    mediaPlayer?.setOnPreparedListener {
                        btn_play_music.isClickable = true
                        slider.value = 0F
                        PlayProgress.hide()
                        btn_play_music.setImageResource(R.drawable.ic_pause)
                        slider.valueTo = it.duration.toFloat()
                        it.start()

                        timer = Timer()
                        timer?.schedule(object : TimerTask() {
                            override fun run() {
                                runOnUiThread {
                                    it.currentPosition.let { current ->
                                        val currentPosition: Float = current.toFloat()
                                        if (!isDragging && currentPosition <= it.duration.toFloat())
                                            slider.value = currentPosition
                                    }

                                }
                            }

                        }, 1000, 1000)

                        it.setOnCompletionListener {
                            slider.value = 0F
                            btn_play_music.setImageResource(R.drawable.ic_play)
                        }
                    }
                } else {
                    if (mediaPlayer?.isPlaying == true) {
                        btn_play_music.setImageResource(R.drawable.ic_play)
                        mediaPlayer?.pause()
                    } else {
                        btn_play_music.setImageResource(R.drawable.ic_pause)
                        mediaPlayer?.start()
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        registerReceiver(onCompleteDownload, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null

        timer?.cancel()

        unregisterReceiver(onCompleteDownload)

    }


    private fun download(title: String, description: String?, uri: String) {
        if (checkDownloaded(title)) {
            return
        }

        val request = DownloadManager.Request(Uri.parse(uri))
        val appName = getString(R.string.app_name)
        val file = File(getExternalFilesDir(DIRECTORY_DOWNLOADS), "${title}.mp3")
        request.setTitle("$appName: $title")

        description?.let {
            request.setDescription(description)
        }

        request.setDestinationUri(Uri.fromFile(file))
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)


        downloadManager?.enqueue(request)
    }

    private fun checkDownloaded(title: String): Boolean {
        val file = File(getExternalFilesDir(DIRECTORY_DOWNLOADS), "${title}.mp3")
        return file.isFile
    }

    private fun getMusicFile(title: String): File {
        val file = File(getExternalFilesDir(DIRECTORY_DOWNLOADS), "${title}.mp3")
        return file
    }

    private val onCompleteDownload: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            img_download.visibility = View.GONE
        }
    }


}