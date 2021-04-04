package amin.rz3.musicplayeronline.data.repo.source

import amin.rz3.musicplayeronline.data.Banner
import io.reactivex.Single

interface BannerDataSource {
    fun getBanners():Single<List<Banner>>
}