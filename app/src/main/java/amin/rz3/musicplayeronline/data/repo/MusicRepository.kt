package amin.rz3.musicplayeronline.data.repo

import amin.rz3.musicplayeronline.data.Music
import io.reactivex.Single

interface MusicRepository {
    fun getMusics(category_id:Int?): Single<List<Music>>
}