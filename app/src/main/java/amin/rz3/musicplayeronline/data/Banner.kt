package amin.rz3.musicplayeronline.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Banner(
    val id: Int,
    val image_url: String
):Parcelable


/*{"id":1,
   "image_url":"https:\/\/s17.picofile.com\/file\/8429645276\/banner1.jpg"}*/