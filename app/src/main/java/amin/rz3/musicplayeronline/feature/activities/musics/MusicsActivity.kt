package amin.rz3.musicplayeronline.feature.activities.musics

import amin.rz3.musicplayeronline.R
import amin.rz3.musicplayeronline.common.EXTRA_KEY_DATA
import amin.rz3.musicplayeronline.common.MusicPlayerOnlineActivity
import amin.rz3.musicplayeronline.data.Category
import amin.rz3.musicplayeronline.data.Music
import amin.rz3.musicplayeronline.feature.activities.playmusic.PlayMusic
import amin.rz3.musicplayeronline.feature.adapter.MusicAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_musics.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MusicsActivity : MusicPlayerOnlineActivity(), MusicAdapter.SetOnClick {
    val musicAdapter: MusicAdapter by inject()
    val musicsViewModel: MusicsViewModel by viewModel {
        parametersOf(
            intent.extras?.getParcelable<Category>(
                EXTRA_KEY_DATA
            )?.id
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_musics)

        recycle_musics_all.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recycle_musics_all.adapter = musicAdapter
        musicAdapter.setOnClick = this


        musicsViewModel.musicLiveData.observe(this) {
            musicAdapter.musicArray = it as ArrayList<Music>
        }


        img_back_musics.setOnClickListener {
            finish()
        }
    }

    override fun onClick(music: Music) {
        startActivity(Intent(this, PlayMusic::class.java).apply {
            putExtra(EXTRA_KEY_DATA, music)
        })
    }
}