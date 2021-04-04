package amin.rz3.musicplayeronline.data.repo

import amin.rz3.musicplayeronline.data.Banner
import amin.rz3.musicplayeronline.data.repo.source.BannerDataSource
import amin.rz3.musicplayeronline.data.repo.source.BannerRemoteDataSource
import io.reactivex.Single

class BannerRepositoryImpl(val bannerDataSource: BannerDataSource):BannerRepository {
    override fun getBanners(): Single<List<Banner>> = bannerDataSource.getBanners()
}