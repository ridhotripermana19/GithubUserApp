package com.example.consumerapp.db

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    /** Authority yang digunakan**/
    const val AUTHORITY = "com.example.githubapps"
    const val SCHEME = "content"

    /** Penggunaan BaseColumns akan memudahkan dalam penggunaan suatu table
     * Untuk id yang autoincrement sudah default ada di dalam class BaseColumns dengan nama field _ID**/
    class FavoriteColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "favorite"
            const val _ID = "_id"
            const val NAME = "name"
            const val FULLNAME = "fullname"
            const val IMAGE = "image"
            const val LOCATION = "location"
            const val COMPANY = "company"

            /** untuk membuat URI content://com.example.mynotesapp/note **/
            /** Base content yang digunakan untuk ases content provider**/
            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}