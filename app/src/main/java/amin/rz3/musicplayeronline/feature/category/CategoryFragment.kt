package amin.rz3.musicplayeronline.feature.category

import amin.rz3.musicplayeronline.R
import amin.rz3.musicplayeronline.common.EXTRA_KEY_DATA
import amin.rz3.musicplayeronline.common.MusicPlayerOnlineFragment
import amin.rz3.musicplayeronline.common.convertDpToPixel
import amin.rz3.musicplayeronline.data.Category
import amin.rz3.musicplayeronline.feature.activities.musics.MusicsActivity
import amin.rz3.musicplayeronline.feature.activities.playmusic.PlayMusic
import amin.rz3.musicplayeronline.feature.adapter.CategoryAdapter
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_category.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel


class CategoryFragment : MusicPlayerOnlineFragment(),CategoryAdapter.OnClickCategory {
    val categoryViewModel:CategoryViewModel by viewModel()
    val categoryAdapter:CategoryAdapter by inject()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycle_category_all.post {
            val sColumnWidth = 110
            val spanCount = Math.floor((recycle_category_all.width / convertDpToPixel(sColumnWidth.toFloat(),requireContext())).toDouble())
            recycle_category_all.layoutManager = GridLayoutManager(requireContext(),spanCount.toInt())
            recycle_category_all.adapter = categoryAdapter
        }
        categoryAdapter.onClickCategory = this
        categoryViewModel.categoryLiveData.observe(viewLifecycleOwner){
            categoryAdapter.categoryArray = it as ArrayList<Category>
        }

        categoryViewModel.progressbarLiveData.observe(viewLifecycleOwner){
            setProgressIndicator(it)
        }
    }

    override fun onClick(category: Category) {
        startActivity(Intent(requireContext(), MusicsActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA, category)
        })
    }

}