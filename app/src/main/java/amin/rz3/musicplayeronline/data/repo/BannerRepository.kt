package amin.rz3.musicplayeronline.data.repo

import amin.rz3.musicplayeronline.data.Banner
import io.reactivex.Single

interface BannerRepository {
    fun getBanners():Single<List<Banner>>
}