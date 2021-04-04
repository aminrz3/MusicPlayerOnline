package amin.rz3.musicplayeronline.feature.category

import amin.rz3.musicplayeronline.R
import amin.rz3.musicplayeronline.common.EXTRA_KEY_DATA
import amin.rz3.musicplayeronline.common.MusicPlayerOnlineActivity
import amin.rz3.musicplayeronline.common.convertDpToPixel
import amin.rz3.musicplayeronline.data.Category
import amin.rz3.musicplayeronline.feature.activities.musics.MusicsActivity
import amin.rz3.musicplayeronline.feature.adapter.CategoryAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.fragment_category.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class CategoryActivity : MusicPlayerOnlineActivity(),CategoryAdapter.OnClickCategory {
    val categoryAdapter: CategoryAdapter by inject()
    val categoryViewModel: CategoryViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        recycle_category_all_activity.post {
            val sColumnWidth = 110
            val spanCount = Math.floor(
                (recycle_category_all_activity.width / convertDpToPixel(
                    sColumnWidth.toFloat(),
                    this
                )).toDouble()
            )
            recycle_category_all_activity.layoutManager =
                GridLayoutManager(this, spanCount.toInt())
            recycle_category_all_activity.adapter = categoryAdapter
        }

        categoryViewModel.categoryLiveData.observe(this){
            categoryAdapter.categoryArray = it as ArrayList<Category>
        }
        categoryAdapter.onClickCategory = this

        img_back_category.setOnClickListener {
            finish()
        }
    }

    override fun onClick(category: Category) {
        startActivity(Intent(this, MusicsActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA, category)
        })
    }
}