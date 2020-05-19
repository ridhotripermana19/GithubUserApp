package com.example.githubapps.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.githubapps.db.DatabaseContract.AUTHORITY
import com.example.githubapps.db.DatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import com.example.githubapps.db.DatabaseContract.FavoriteColumns.Companion.TABLE_NAME
import com.example.githubapps.db.FavoriteHelper

class FavoriteProvider : ContentProvider() {

    companion object {
        /** Integer digunakan sebagai identifier antara
         * sekecr all & select by id**/
        private const val FAVORITE = 1
        private const val FAVORITE_ID = 2
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var favoriteHelper: FavoriteHelper

        /** Uri matcher untuk mempermudah identifier dengan menggunakan integer
         * misal
         * uri com.example.mynotesapp dicocokan dengan integer 1
         * uri com.example.mynotesapp/# dicocokan dengan integer 2**/
        init {
            // content://com.example.mynotesapp/note
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, FAVORITE)

            // content://com.example.mynotesapp/note/id
            sUriMatcher.addURI(AUTHORITY, "$TABLE_NAME/#", FAVORITE_ID)
        }
    }

    override fun onCreate(): Boolean {
        favoriteHelper = FavoriteHelper.getInstance(context as Context)
        favoriteHelper.open()
        return true
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        val cursor: Cursor?
        when (sUriMatcher.match(uri)) {
            FAVORITE -> cursor = favoriteHelper.queryAll()
            FAVORITE_ID -> cursor = favoriteHelper.queryById(uri.lastPathSegment.toString())
            else -> cursor = null
        }
        return cursor
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added: Long = when (FAVORITE) {
            sUriMatcher.match(uri) -> favoriteHelper.insert(values)
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        val updated: Int = when (FAVORITE_ID) {
            sUriMatcher.match(uri) -> favoriteHelper.update(uri.lastPathSegment.toString(), values)
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return updated
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val deleted: Int = when (FAVORITE_ID) {
            sUriMatcher.match(uri) -> favoriteHelper.deleteById(uri.lastPathSegment.toString())
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return deleted
    }
}
