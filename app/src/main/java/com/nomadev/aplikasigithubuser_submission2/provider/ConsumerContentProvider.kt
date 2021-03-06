package com.nomadev.aplikasigithubuser_submission2.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.nomadev.aplikasigithubuser_submission2.data.local.FavoriteUserDao
import com.nomadev.aplikasigithubuser_submission2.data.local.UserDatabase

class ConsumerContentProvider : ContentProvider() {

    companion object {
        private const val AUTHORITY = "com.nomadev.aplikasigithubuser_submission2"
        private const val TABLE_NAME = "favorite_user"
        const val ID_FAV_USER_DATA = 1
        val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(AUTHORITY, TABLE_NAME, ID_FAV_USER_DATA)
        }
    }

    private lateinit var userDao: FavoriteUserDao

    override fun onCreate(): Boolean {
        userDao = context.let {
            UserDatabase.getDatabase(it!!)?.favoriteUserDao()!!
        }
        return false
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val cursor: Cursor?
        when (uriMatcher.match(uri)) {
            ID_FAV_USER_DATA -> {
                cursor = userDao.findAll()
                if (context != null) {
                    cursor.setNotificationUri(context?.contentResolver, uri)
                }
            }
            else -> {
                cursor = null
            }
        }
        return cursor
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int = 0

    override fun getType(uri: Uri): String? = null

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int = 0
}