package amin.rz3.musicplayeronline.feature.home

import amin.rz3.musicplayeronline.common.MusicPlayerOnlineViewModel
import amin.rz3.musicplayeronline.common.MusicPlayerSignleObserver
import amin.rz3.musicplayeronline.data.Banner
import amin.rz3.musicplayeronline.data.Category
import amin.rz3.musicplayeronline.data.Music
import amin.rz3.musicplayeronline.data.repo.BannerRepository
import amin.rz3.musicplayeronline.data.repo.CategoryRepository
import amin.rz3.musicplayeronline.data.repo.MusicRepository
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel(bannerRepository: BannerRepository,musicRepository: MusicRepository,categoryRepository: CategoryRepository):MusicPlayerOnlineViewModel() {
    val bannerLiveData = MutableLiveData<List<Banner>>()
    val progressLiveData = MutableLiveData<Boolean>()
    val musicLiveData = MutableLiveData<List<Music>>()
    val lastCategoryLiveData = MutableLiveData<List<Category>>()
    init {
        bannerRepository.getBanners()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : MusicPlayerSignleObserver<List<Banner>>(compositeDisposable){
                override fun onSuccess(t: List<Banner>) {
                    bannerLiveData.value = t
                }
            })



        musicRepository.getMusics(null)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                progressLiveData.value = false
            }
            .subscribe(object : MusicPlayerSignleObserver<List<Music>>(compositeDisposable) {
                override fun onSuccess(t: List<Music>) {
                    musicLiveData.value = t
                }
            })


        categoryRepository.getLastCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { progressLiveData.value=false }
            .subscribe(object: MusicPlayerSignleObserver<List<Category>>(compositeDisposable){
                override fun onSuccess(t: List<Category>) {
                    lastCategoryLiveData.value = t
                }

            })
    }
}