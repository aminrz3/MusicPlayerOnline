package amin.rz3.musicplayeronline.feature.adapter

import amin.rz3.musicplayeronline.data.Banner
import amin.rz3.musicplayeronline.feature.home.BannerFragment
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class BannerAdapter(fragment: Fragment, val banners:List<Banner>):FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = banners.size

    override fun createFragment(position: Int): Fragment =
        BannerFragment.newInstance(banners[position])
}