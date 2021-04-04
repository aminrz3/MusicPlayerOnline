package amin.rz3.musicplayeronline.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Music(
    val category_id: Int,
    val id: Int,
    val image_url: String,
    val music_url: String,
    val name: String,
    val Singer: String,
    val time: String
):Parcelable



/*
    {"id":200,
    "category_id":10,
    "name":"Selfish Love (with Selena Gomez)",
    "Singer":"DJ Snake","time":"02:48",
    "image_url":"https:\/\/music-daily.ir\/wp-content\/uploads\/2021\/03\/5.DJ-Snake-Selena-Gomez-Selfish-Love.jpg",
    "music_url":"https:\/\/musicfeed.ir\/files\/dir\/2021\/3\/DJ%20Snake%20Selfish%20Love%20(with%20Selena%20Gomez).mp3"}
*/