package com.nomadev.consumerapp.utils

import android.database.Cursor
import com.nomadev.consumerapp.model.ItemsModel

object MappingHelper {
    fun mapCursorToArrayList(cursor: Cursor?): ArrayList<ItemsModel> {
        val list = ArrayList<ItemsModel>()
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val id =
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumns.ID))
                val username =
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumns.USERNAME))
                val avatarUrl =
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumns.AVATAR_URL))
                val htmlUrl =
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumns.HTML_URL))

                list.add(ItemsModel(username, id, avatarUrl, htmlUrl))
            }
        }
        return list
    }
}