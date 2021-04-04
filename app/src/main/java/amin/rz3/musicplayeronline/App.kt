package amin.rz3.musicplayeronline

import amin.rz3.musicplayeronline.data.repo.*
import amin.rz3.musicplayeronline.data.repo.source.BannerRemoteDataSource
import amin.rz3.musicplayeronline.data.repo.source.CategoryRemoteDataSource
import amin.rz3.musicplayeronline.data.repo.source.MusicRemoteDataSource
import amin.rz3.musicplayeronline.feature.activities.musics.MusicsViewModel
import amin.rz3.musicplayeronline.feature.activities.playmusic.PlayMusicViewModel
import amin.rz3.musicplayeronline.feature.adapter.CategoryAdapter
import amin.rz3.musicplayeronline.feature.adapter.MusicAdapter
import amin.rz3.musicplayeronline.feature.category.CategoryViewModel
import amin.rz3.musicplayeronline.feature.home.HomeViewModel
import amin.rz3.musicplayeronline.services.FrescoImageLoadingServiceImpl
import amin.rz3.musicplayeronline.services.ImageLoadingService
import amin.rz3.musicplayeronline.services.http.createApiService
import android.app.Application
import android.os.Bundle
import com.facebook.drawee.backends.pipeline.Fresco
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Fresco.initialize(this)

        val myModules = module {
            single { createApiService() }
            single<ImageLoadingService> { FrescoImageLoadingServiceImpl() }
            factory<BannerRepository> { BannerRepositoryImpl(BannerRemoteDataSource(get())) }
            factory<CategoryRepository> { CategoryRepositoryImpl(CategoryRemoteDataSource(get())) }
            factory { CategoryAdapter(get()) }
            factory<MusicRepository> { MusicRepositoryImpl(MusicRemoteDataSource(get())) }
            factory { MusicAdapter(get()) }
            viewModel { HomeViewModel(get(),get(),get()) }
            viewModel { CategoryViewModel(get()) }
            viewModel { (category_id:Int?)->MusicsViewModel(category_id,get()) }
            viewModel { (bundle:Bundle)->PlayMusicViewModel(bundle) }
        }

        startKoin {
            androidContext(this@App)
            modules(myModules)
        }
    }
}