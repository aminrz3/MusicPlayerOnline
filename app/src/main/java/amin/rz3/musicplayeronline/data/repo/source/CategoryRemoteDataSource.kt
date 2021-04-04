package amin.rz3.musicplayeronline.data.repo.source

import amin.rz3.musicplayeronline.data.Category
import amin.rz3.musicplayeronline.services.http.ApiService
import io.reactivex.Single

class CategoryRemoteDataSource(val apiService: ApiService):CategoryDataSource {
    override fun getCategories(): Single<List<Category>> = apiService.getCategories()
    override fun getLastCategories(): Single<List<Category>> = apiService.getLastCategories()
}