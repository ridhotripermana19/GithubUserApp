package com.example.githubapps.widget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Binder
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubapps.R
import com.example.githubapps.db.FavoriteHelper
import com.example.githubapps.model.favorite.FavoriteModel
import com.example.githubapps.utils.MappingHelper

internal class StackRemoteViewsFactory(private val mContext: Context) : RemoteViewsService.RemoteViewsFactory {

    private lateinit var favoriteHelper: FavoriteHelper
    private val mWidgetItems: ArrayList<FavoriteModel> = ArrayList()

    override fun onCreate() {
        val identityToken = Binder.clearCallingIdentity()
        favoriteHelper = FavoriteHelper.getInstance(mContext)
        favoriteHelper.open()
        Binder.restoreCallingIdentity(identityToken)
    }
    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onDataSetChanged() {
        try {
            val favorite = favoriteHelper.queryAll()
            val listFavorite = MappingHelper.mapCursorToArrayList(favorite)
            mWidgetItems.clear()
            mWidgetItems.addAll(listFavorite)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.item_widget)
        try {
            val bitmap: Bitmap =
                Glide.with(mContext)
                .asBitmap()
                    .load(mWidgetItems[position].image)
                    .apply(RequestOptions.fitCenterTransform())
                    .submit(800, 550)
                    .get()
            rv.setImageViewBitmap(R.id.imageView, bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val extras = Bundle()
        extras.putInt(FavoriteBannerWidget.EXTRA_ITEM, position)
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent)
        return rv
    }

    override fun getCount(): Int {
        return mWidgetItems.size
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun onDestroy() {
        favoriteHelper.close()
    }


}

