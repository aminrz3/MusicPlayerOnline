package amin.rz3.musicplayeronline.data.repo

import amin.rz3.musicplayeronline.data.Music
import amin.rz3.musicplayeronline.data.repo.source.MusicDataSource
import io.reactivex.Single

class MusicRepositoryImpl(val musicDataSource: MusicDataSource):MusicRepository {
    override fun getMusics(category_id:Int?): Single<List<Music>> = musicDataSource.getMusics(category_id)
}