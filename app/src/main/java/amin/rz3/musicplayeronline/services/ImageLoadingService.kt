package amin.rz3.musicplayeronline.services

import amin.rz3.musicplayeronline.view.MusicPlayerOnlineImageView

interface ImageLoadingService {
    fun load(musicPlayerOnlineImageView: MusicPlayerOnlineImageView, imageUrl:String)
}