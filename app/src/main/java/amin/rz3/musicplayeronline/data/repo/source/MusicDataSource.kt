package amin.rz3.musicplayeronline.data.repo.source

import amin.rz3.musicplayeronline.data.Music
import io.reactivex.Single

interface MusicDataSource {
    fun getMusics(category_id:Int?):Single<List<Music>>
}