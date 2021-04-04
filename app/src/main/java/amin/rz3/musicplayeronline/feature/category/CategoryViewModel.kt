package amin.rz3.musicplayeronline.feature.category

import amin.rz3.musicplayeronline.common.MusicPlayerOnlineViewModel
import amin.rz3.musicplayeronline.common.MusicPlayerSignleObserver
import amin.rz3.musicplayeronline.data.Category
import amin.rz3.musicplayeronline.data.repo.CategoryRepository
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CategoryViewModel(categoryRepository: CategoryRepository):MusicPlayerOnlineViewModel() {
    val categoryLiveData = MutableLiveData<List<Category>>()
    val progressbarLiveData = MutableLiveData<Boolean>()
    init {
        progressbarLiveData.value = true
        categoryRepository.getCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                progressbarLiveData.value = false
            }
            .subscribe(object : MusicPlayerSignleObserver<List<Category>>(compositeDisposable){
                override fun onSuccess(t: List<Category>) {
                    categoryLiveData.value = t
                }
            })
    }
}