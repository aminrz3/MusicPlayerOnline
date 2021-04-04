package amin.rz3.musicplayeronline.feature.activities.musics

import amin.rz3.musicplayeronline.common.MusicPlayerOnlineViewModel
import amin.rz3.musicplayeronline.common.MusicPlayerSignleObserver
import amin.rz3.musicplayeronline.data.Music
import amin.rz3.musicplayeronline.data.repo.MusicRepository
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MusicsViewModel(categoy_id:Int?, musicRepository: MusicRepository):MusicPlayerOnlineViewModel() {
    val musicLiveData = MutableLiveData<List<Music>>()
    val progressLiveData = MutableLiveData<Boolean>()

    init {
        progressLiveData.value = true

        musicRepository.getMusics(categoy_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                progressLiveData.value = false
            }
            .subscribe(object : MusicPlayerSignleObserver<List<Music>>(compositeDisposable) {
                override fun onSuccess(t: List<Music>) {
                    musicLiveData.value = t
                }
            })
    }
}