package com.example.consumerapp.utils.mappingHelper

import android.database.Cursor
import com.example.consumerapp.db.DatabaseContract
import com.example.consumerapp.model.FavoriteModel

object MappingHelper {

    fun mapCursorToArrayList(favoritesCursor: Cursor?): ArrayList<FavoriteModel> {
        val favoriteList = ArrayList<FavoriteModel>()
        favoritesCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns._ID))
                val name = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.NAME))
                val fullname = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.FULLNAME))
                val image = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.IMAGE))
                val location = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.LOCATION))
                val company = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.COMPANY))
                favoriteList.add(
                    FavoriteModel(
                        id,
                        name,
                        fullname,
                        image,
                        location,
                        company
                    )
                )
            }
        }
        return favoriteList
    }

    fun mapCursorToObject(favoritesCursor: Cursor?): FavoriteModel {
        var note = FavoriteModel()
        favoritesCursor?.apply {
            moveToFirst()
            val id = getInt(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns._ID))
            val name = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.NAME))
            val fullname = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.FULLNAME))
            val image = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.IMAGE))
            val location = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.LOCATION))
            val company = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.COMPANY))
            note = FavoriteModel(
                id,
                name,
                fullname,
                image,
                location,
                company
            )
        }
        return note
    }
}