package amin.rz3.musicplayeronline.feature.adapter

import amin.rz3.musicplayeronline.R
import amin.rz3.musicplayeronline.common.implementSpringAnimationTrait
import amin.rz3.musicplayeronline.data.Category
import amin.rz3.musicplayeronline.services.ImageLoadingService
import amin.rz3.musicplayeronline.view.MusicPlayerOnlineImageView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

class CategoryAdapter(val imageLoadingService: ImageLoadingService) :
    RecyclerView.Adapter<CategoryAdapter.viewHolder>() {

    var onClickCategory:OnClickCategory?=null
    var categoryArray = ArrayList<Category>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    inner class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val categoryTv: TextView = itemView.findViewById(R.id.tv_category)
        val categoryIv: MusicPlayerOnlineImageView = itemView.findViewById(R.id.img_category)

        fun bindCategory(category: Category) {
            categoryTv.setText(category.name + " " + category.family)
            imageLoadingService.load(categoryIv, category.image_url)

            itemView.implementSpringAnimationTrait()
            itemView.setOnClickListener{
                onClickCategory?.onClick(category)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) =
        holder.bindCategory(categoryArray.get(position))

    override fun getItemCount(): Int = categoryArray.size

    interface OnClickCategory{
        fun onClick(category: Category)
    }
}