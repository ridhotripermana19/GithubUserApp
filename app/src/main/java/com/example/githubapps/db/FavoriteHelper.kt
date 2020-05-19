package com.example.githubapps.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.githubapps.db.DatabaseContract.FavoriteColumns.Companion.NAME
import com.example.githubapps.db.DatabaseContract.FavoriteColumns.Companion.TABLE_NAME
import com.example.githubapps.db.DatabaseContract.FavoriteColumns.Companion._ID

class FavoriteHelper(context: Context) {


    private var dataBaseHelper: DatabaseHelper =
        DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private var INSTANCE: FavoriteHelper? = null

        fun getInstance(context: Context): FavoriteHelper =
            INSTANCE
                ?: synchronized(this) {
                INSTANCE
                    ?: FavoriteHelper(context)
            }
    }

    @Throws(android.database.SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    fun close() {
        dataBaseHelper.close()

        if (database.isOpen)
            database.close()
    }

    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID DESC",
            null
        )
    }

    fun queryById(id: String): Cursor {
        open()
        return database.query(
            DATABASE_TABLE,
            null,
            "$_ID = ?",
            arrayOf(id),
            null,
            null,
            null,
            null
        )
    }

    fun insert(values: ContentValues?): Long {
        open()
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun update(id: String, values: ContentValues?): Int {
        open()
        return database.update(DATABASE_TABLE, values, "$_ID = ?", arrayOf(id))
    }

    fun deleteById(id: String): Int {
        open()
        return database.delete(DATABASE_TABLE, "$_ID = '$id'", null)
    }

    fun checkUserName(username: String) : Boolean {
        open()
        val columns = arrayOf(_ID)
        val selection = NAME + " = ?"
        val selectionArgs = arrayOf(username)
        val cursor = database.query(
            TABLE_NAME,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        val cursorCount = cursor.count
        cursor.close()
        database.close()
        return if (cursorCount > 0) {
            false
        } else
            return true
    }
}