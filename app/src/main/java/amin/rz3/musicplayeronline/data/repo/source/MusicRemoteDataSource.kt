package amin.rz3.musicplayeronline.data.repo.source

import amin.rz3.musicplayeronline.data.Music
import amin.rz3.musicplayeronline.services.http.ApiService
import io.reactivex.Single

class MusicRemoteDataSource(val apiService: ApiService):MusicDataSource {
    override fun getMusics(category_id:Int?): Single<List<Music>> = apiService.getMusics(category_id)
}