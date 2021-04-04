package amin.rz3.musicplayeronline.data.repo.source

import amin.rz3.musicplayeronline.data.Category
import io.reactivex.Single

interface CategoryDataSource {
    fun getCategories(): Single<List<Category>>
    fun getLastCategories(): Single<List<Category>>
}