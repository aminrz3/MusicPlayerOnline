
package amin.rz3.musicplayeronline.feature.adapter

import amin.rz3.musicplayeronline.R
import amin.rz3.musicplayeronline.common.implementSpringAnimationTrait
import amin.rz3.musicplayeronline.data.Music
import amin.rz3.musicplayeronline.services.ImageLoadingService
import amin.rz3.musicplayeronline.view.MusicPlayerOnlineImageView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MusicAdapter(val imageLoadingService: ImageLoadingService): RecyclerView.Adapter<MusicAdapter.viewHolder>() {
    var setOnClick:SetOnClick?=null
    var musicArray = ArrayList<Music>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    inner class viewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        val img_music:MusicPlayerOnlineImageView = itemView.findViewById(R.id.img_music)
        val title_music:TextView = itemView.findViewById(R.id.title_music)
        val singer_music:TextView = itemView.findViewById(R.id.singer_music)
        val time_music:TextView = itemView.findViewById(R.id.time_music)

        fun bind(music: Music){
            imageLoadingService.load(img_music,music.image_url)
            title_music.text = music.name
            singer_music.text = music.Singer
            time_music.text = music.time

            itemView.implementSpringAnimationTrait()
            itemView.setOnClickListener {
                setOnClick?.onClick(music)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.music_item,parent,false)
        )
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bind(musicArray.get(position))
    }

    override fun getItemCount(): Int = musicArray.size


    interface SetOnClick{
        fun onClick(music: Music)
    }
}