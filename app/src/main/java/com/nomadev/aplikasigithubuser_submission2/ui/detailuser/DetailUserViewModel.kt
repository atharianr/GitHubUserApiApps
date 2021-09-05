package com.nomadev.aplikasigithubuser_submission2.ui.detailuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nomadev.aplikasigithubuser_submission2.domain.model.UserResponse
import com.nomadev.aplikasigithubuser_submission2.data.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel : ViewModel() {

    val user = MutableLiveData<UserResponse>()
    val status = MutableLiveData<Boolean>()

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
}