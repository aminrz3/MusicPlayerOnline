package amin.rz3.musicplayeronline.data.repo.source

import amin.rz3.musicplayeronline.data.Banner
import amin.rz3.musicplayeronline.services.http.ApiService
import io.reactivex.Single

class BannerRemoteDataSource(val apiService: ApiService):BannerDataSource {
    override fun getBanners(): Single<List<Banner>> = apiService.getBanners()
}