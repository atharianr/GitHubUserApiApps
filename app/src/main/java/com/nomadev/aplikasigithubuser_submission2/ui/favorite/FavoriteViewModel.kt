package com.nomadev.aplikasigithubuser_submission2.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.nomadev.aplikasigithubuser_submission2.data.local.FavoriteUser
import com.nomadev.aplikasigithubuser_submission2.data.local.FavoriteUserDao
import com.nomadev.aplikasigithubuser_submission2.data.local.UserDatabase

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    private var userDao: FavoriteUserDao?
    private var userDb: UserDatabase?

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }

    fun getFavoriteUser() : LiveData<List<FavoriteUser>>? {
        return userDao?.getFavoriteUser()
    }
}