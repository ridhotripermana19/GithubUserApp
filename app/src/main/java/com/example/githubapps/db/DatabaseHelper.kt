package com.example.githubapps.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.githubapps.db.DatabaseContract.FavoriteColumns.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context,
    DATABASE_NAME, null,
    DATABASE_VERSION
) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_FAVORITE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "favorite.db"
        private const val DATABASE_VERSION = 1
        private val SQL_CREATE_TABLE_FAVORITE = "CREATE TABLE $TABLE_NAME" +
                " (${DatabaseContract.FavoriteColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${DatabaseContract.FavoriteColumns.NAME} TEXT UNIQUE," +
                " ${DatabaseContract.FavoriteColumns.FULLNAME} TEXT," +
                " ${DatabaseContract.FavoriteColumns.IMAGE} TEXT," +
                " ${DatabaseContract.FavoriteColumns.LOCATION} TEXT," +
                " ${DatabaseContract.FavoriteColumns.COMPANY} TEXT)"
    }
}