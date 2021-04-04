package amin.rz3.musicplayeronline.feature.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import amin.rz3.musicplayeronline.R
import amin.rz3.musicplayeronline.common.EXTRA_KEY_DATA
import amin.rz3.musicplayeronline.data.Banner
import amin.rz3.musicplayeronline.services.ImageLoadingService
import amin.rz3.musicplayeronline.view.MusicPlayerOnlineImageView
import org.koin.android.ext.android.inject
import java.lang.IllegalStateException


class BannerFragment : Fragment() {
    val imageLoadingService : ImageLoadingService by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val imageView =  inflater.inflate(R.layout.fragment_banner, container, false) as MusicPlayerOnlineImageView
        val banner = requireArguments().getParcelable<Banner>(EXTRA_KEY_DATA)?: throw IllegalStateException("Banner Cannot Be null")
        imageLoadingService.load(imageView,banner.image_url)
        return imageView
    }

    companion object{
        fun newInstance(banner: Banner):BannerFragment{
            return BannerFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_KEY_DATA,banner)
                }
            }
        }
    }

}