package com.nomadev.consumerapp.ui.favorite

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nomadev.consumerapp.model.ItemsModel
import com.nomadev.consumerapp.utils.DatabaseContract
import com.nomadev.consumerapp.utils.MappingHelper

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private var list = MutableLiveData<ArrayList<ItemsModel>>()

    fun setFavoriteUser(context: Context) {
        val cursor = context.contentResolver.query(
            DatabaseContract.FavoriteUserColumns.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        val listConverted = MappingHelper.mapCursorToArrayList(cursor)
        list.postValue(listConverted)
    }

    fun getFavoriteUser(): LiveData<ArrayList<ItemsModel>> {
        return list
    }
}