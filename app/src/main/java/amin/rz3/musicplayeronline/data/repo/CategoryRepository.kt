package amin.rz3.musicplayeronline.data.repo

import amin.rz3.musicplayeronline.data.Category
import io.reactivex.Single

interface CategoryRepository {
    fun getCategories():Single<List<Category>>
    fun getLastCategories():Single<List<Category>>
}