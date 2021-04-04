package amin.rz3.musicplayeronline.services.http

import amin.rz3.musicplayeronline.data.Banner
import amin.rz3.musicplayeronline.data.Category
import amin.rz3.musicplayeronline.data.Music
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("banners")
    fun getBanners():Single<List<Banner>>

    @GET("category")
    fun getCategories():Single<List<Category>>

    @GET("lastCategory")
    fun getLastCategories():Single<List<Category>>

    @GET("musics")
    fun getMusics(@Query("id") category_id:Int?):Single<List<Music>>
}

fun createApiService():ApiService{
    val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.208.80:8000/api/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(ApiService::class.java)
}

