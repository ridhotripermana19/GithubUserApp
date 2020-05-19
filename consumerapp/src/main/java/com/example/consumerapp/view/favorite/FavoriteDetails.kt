package com.example.consumerapp.view.favorite

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.consumerapp.R
import com.example.consumerapp.db.DatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import com.example.consumerapp.model.FavoriteModel
import com.example.consumerapp.utils.mappingHelper.MappingHelper
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_favorite_details.*

class FavoriteDetails : AppCompatActivity() {

    private lateinit var uriWithId: Uri
    private var favoriteModel: FavoriteModel? = null
    private var position: Int = 0

    companion object {
        const val EXTRA_FAVORITE = "extra_favorite"
        const val EXTRA_POSITION = "extra_position"
        const val REQUEST_DETAIL = 300
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_details)
        supportActionBar?.title = "Consumer Favorite"

        favoriteModel = intent.getParcelableExtra(EXTRA_FAVORITE)
        if (favoriteModel != null)
            position = intent.getIntExtra(EXTRA_POSITION, 0)
        else
            favoriteModel = FavoriteModel()

        uriWithId = Uri.parse(CONTENT_URI.toString() + "/" + favoriteModel?.id)
        val cursor = contentResolver.query(uriWithId, null, null, null, null)
        if (cursor != null) {
            favoriteModel = MappingHelper.mapCursorToObject(cursor)
            cursor.close()
        }

        favoriteModel?.let {
            user_fav_detail.text = it.name
            Picasso.get().load(it.image).into(img_fav_detail)
            tv_fav_name_detail.text = it.fullname
            tv_fav_location_detail.text = it.location
            tv_fav_company_detail.text = it.company
        }
    }
}
