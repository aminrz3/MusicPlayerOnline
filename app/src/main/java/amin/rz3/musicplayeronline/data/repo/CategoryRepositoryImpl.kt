package amin.rz3.musicplayeronline.data.repo

import amin.rz3.musicplayeronline.data.Category
import amin.rz3.musicplayeronline.data.repo.source.CategoryDataSource
import io.reactivex.Single

class CategoryRepositoryImpl(val categoryDataSource:CategoryDataSource):CategoryRepository {
    override fun getCategories(): Single<List<Category>> = categoryDataSource.getCategories()
    override fun getLastCategories(): Single<List<Category>> = categoryDataSource.getLastCategories()
}