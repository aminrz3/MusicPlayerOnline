package amin.rz3.musicplayeronline.feature.home

import amin.rz3.musicplayeronline.R
import amin.rz3.musicplayeronline.common.EXTRA_KEY_DATA
import amin.rz3.musicplayeronline.common.MusicPlayerOnlineFragment
import amin.rz3.musicplayeronline.common.convertDpToPixel
import amin.rz3.musicplayeronline.data.Category
import amin.rz3.musicplayeronline.data.Music
import amin.rz3.musicplayeronline.feature.activities.musics.MusicsActivity
import amin.rz3.musicplayeronline.feature.activities.musics.MusicsViewModel
import amin.rz3.musicplayeronline.feature.activities.playmusic.PlayMusic
import amin.rz3.musicplayeronline.feature.adapter.BannerAdapter
import amin.rz3.musicplayeronline.feature.adapter.CategoryAdapter
import amin.rz3.musicplayeronline.feature.adapter.MusicAdapter
import amin.rz3.musicplayeronline.feature.category.CategoryActivity
import amin.rz3.musicplayeronline.feature.category.CategoryViewModel
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : MusicPlayerOnlineFragment(), MusicAdapter.SetOnClick,
    CategoryAdapter.OnClickCategory {
    private var currentPage = 0
    val homeViewModel: HomeViewModel by viewModel()
    val categoryAdapter: CategoryAdapter by inject()
    val musicAdapter: MusicAdapter by inject()
    var bannerAdapter: BannerAdapter? = null
    var timer: Timer? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycle_category.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        recycle_category.adapter = categoryAdapter
        categoryAdapter.onClickCategory = this

        recycle_musics.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        recycle_musics.adapter = musicAdapter
        musicAdapter.setOnClick = this

        homeViewModel.bannerLiveData.observe(viewLifecycleOwner) {
            bannerAdapter = BannerAdapter(this, it)
            bannerSliderViewPager.adapter = bannerAdapter
            bannerSliderViewPager.post {
                val viewPagerHeight = (((bannerSliderViewPager.width - convertDpToPixel(
                    32f,
                    requireContext()
                )) * 173) / 328).toInt()
                val layoutParams = bannerSliderViewPager.layoutParams
                layoutParams.height = viewPagerHeight
                bannerSliderViewPager.layoutParams = layoutParams
            }

            bannerIndicator.setViewPager2(bannerSliderViewPager)

            autoSlide();
        }

        homeViewModel.lastCategoryLiveData.observe(viewLifecycleOwner) {
            categoryAdapter.categoryArray = it as ArrayList<Category>
        }

        homeViewModel.musicLiveData.observe(viewLifecycleOwner) {
            musicAdapter.musicArray = it as ArrayList<Music>
        }

        homeViewModel.progressLiveData.observe(viewLifecycleOwner) {
            setProgressIndicator(it)
        }

        btn_all_music.setOnClickListener {
            startActivity(Intent(requireContext(), MusicsActivity::class.java))
        }

        btn_all_category.setOnClickListener {
            startActivity(Intent(requireContext(), CategoryActivity::class.java))
        }
    }


    fun autoSlide() {
        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                activity?.runOnUiThread {
                    if (currentPage == (bannerAdapter!!.itemCount + 1) - 1) {
                        currentPage = 0;
                    }
                    bannerSliderViewPager.setCurrentItem(currentPage++, true);
                }
            }
        }, 1000, 3000)

    }

    override fun onClick(music: Music) {
        startActivity(Intent(requireContext(), PlayMusic::class.java).apply {
            putExtra(EXTRA_KEY_DATA, music)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }

    override fun onStop() {
        super.onStop()
        timer?.cancel()
    }

    override fun onClick(category: Category) {
        startActivity(Intent(requireContext(), MusicsActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA, category)
        })
    }

}