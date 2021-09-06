package com.nomadev.aplikasigithubuser_submission2.ui.detailuser

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nomadev.aplikasigithubuser_submission2.data.local.FavoriteUser
import com.nomadev.aplikasigithubuser_submission2.data.local.FavoriteUserDao
import com.nomadev.aplikasigithubuser_submission2.data.local.UserDatabase
import com.nomadev.aplikasigithubuser_submission2.data.network.RetrofitClient
import com.nomadev.aplikasigithubuser_submission2.domain.model.UserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {

    val user = MutableLiveData<UserResponse>()
    val status = MutableLiveData<Boolean>()

    private var userDao: FavoriteUserDao?
    private var userDb: UserDatabase?

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }

    fun setUserDetail(username: String) {
        RetrofitClient.apiInstance
            .getUser(username)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    status.value = true
                    user.postValue(response.body())
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    status.value = false
                    Log.d("ERROR", t.message.toString())
                }
            })
    }

    fun getUserDetail(): LiveData<UserResponse> {
        return user
    }

    fun addToFavorite(username: String, id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            var user = FavoriteUser(username, id)
            userDao?.addToFavorite(user)
        }
    }

    suspend fun checkUser(id: Int): Int? {
        return userDao?.checkUser(id)
    }

    fun removeFromFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFavorite(id)
        }
    }
}