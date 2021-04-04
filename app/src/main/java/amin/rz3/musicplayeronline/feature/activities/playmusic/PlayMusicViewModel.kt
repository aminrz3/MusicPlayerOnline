package amin.rz3.musicplayeronline.feature.activities.playmusic

import amin.rz3.musicplayeronline.common.EXTRA_KEY_DATA
import amin.rz3.musicplayeronline.common.MusicPlayerOnlineViewModel
import amin.rz3.musicplayeronline.data.Music
import android.os.Bundle
import androidx.lifecycle.MutableLiveData

class PlayMusicViewModel(bundle: Bundle):MusicPlayerOnlineViewModel() {
    val musicLiveData = MutableLiveData<Music>()
    init {
        musicLiveData.value = bundle.getParcelable(EXTRA_KEY_DATA)
    }
}