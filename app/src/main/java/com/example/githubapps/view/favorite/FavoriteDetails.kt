package com.example.githubapps.view.favorite

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.githubapps.R
import com.example.githubapps.db.DatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import com.example.githubapps.model.favorite.FavoriteModel
import com.example.githubapps.utils.MappingHelper
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_favorite_details.*

class FavoriteDetails : AppCompatActivity(), View.OnClickListener {

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
        setTitle(R.string.favoriteDetail)

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

        fab_delete.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        showAlertDeleteDialog()
    }

    private fun showAlertDeleteDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(applicationContext.getString(R.string.delete_favorite_user))
        alertDialogBuilder
            .setMessage(applicationContext.getString(R.string.deleteConfirm))
            .setCancelable(false)
            .setPositiveButton("Ya") { dialog, id ->

                /** Gunakan uriWithId dari intent activity ini
                 * coontent://com.example.mynotesapp/favorite/id **/
                contentResolver.delete(uriWithId, null, null)
                finish()
            }
            .setNegativeButton("Tidak") { dialog, id -> dialog.cancel() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}
