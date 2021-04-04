package amin.rz3.musicplayeronline.services

import amin.rz3.musicplayeronline.view.MusicPlayerOnlineImageView
import com.facebook.drawee.view.SimpleDraweeView
import java.lang.IllegalStateException

class FrescoImageLoadingServiceImpl : ImageLoadingService {
    override fun load(musicPlayerOnlineImageView: MusicPlayerOnlineImageView, imageUrl: String) {
        if (musicPlayerOnlineImageView is SimpleDraweeView) {
            musicPlayerOnlineImageView.setImageURI(imageUrl)
        } else {
            throw IllegalStateException("ImageView Must be instance of SimpleDraweeView")
        }
    }
}