package amin.rz3.musicplayeronline.common

import amin.rz3.musicplayeronline.R
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.loading_view.view.*

abstract class MusicPlayerOnlineViewModel : ViewModel() {
    val compositeDisposable = CompositeDisposable()
    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}

abstract class MusicPlayerOnlineFragment:Fragment(), MusicPlayerOnlineView{
    override val rootView: CoordinatorLayout?
        get() = view as CoordinatorLayout

    override val viewContext: Context?
        get() = context
}

abstract class MusicPlayerOnlineActivity:AppCompatActivity(),MusicPlayerOnlineView{
    override val rootView: CoordinatorLayout?
        get() = window.decorView.rootView as CoordinatorLayout

    override val viewContext: Context?
        get() = this
}


interface MusicPlayerOnlineView {
    val rootView: CoordinatorLayout?
    val viewContext: Context?
    fun setProgressIndicator(show: Boolean) {
        viewContext?.let { context ->
            rootView?.let {
                var loadingView = it.findViewById<View>(R.id.loadingView)

                if (loadingView == null && show) {
                    loadingView = LayoutInflater.from(context).inflate(R.layout.loading_view,it,false)
                    it.addView(loadingView)
                }

                loadingView?.visibility = if(show) View.VISIBLE else View.GONE
            }
        }
    }
}