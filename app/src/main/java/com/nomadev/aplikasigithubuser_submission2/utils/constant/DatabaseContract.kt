package com.nomadev.aplikasigithubuser_submission2.utils.constant

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {
    private const val AUTHORITY = "com.nomadev.aplikasigithubuser_submission2"
    private const val SCHEME = "content"

    internal class FavoriteUserColumns : BaseColumns {
        companion object {
            private const val TABLE_NAME = "favorite_user"
            const val ID = "id"
            const val USERNAME = "login"
            const val AVATAR_URL = "avatarUrl"
            const val HTML_URL = "htmlUrl"

            val CONTENT_URI = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}