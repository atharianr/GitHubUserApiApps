package com.nomadev.aplikasigithubuser_submission2.ui.detailuser.follow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nomadev.aplikasigithubuser_submission2.model.ItemsModel
import com.nomadev.aplikasigithubuser_submission2.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel: ViewModel() {

    val listFollowing = MutableLiveData<ArrayList<ItemsModel>>()

    fun setListFollowing(username: String) {
        RetrofitClient.apiInstance
            .getUserFollowing(username)
            .enqueue(object : Callback<ArrayList<ItemsModel>> {
                override fun onResponse(
                    call: Call<ArrayList<ItemsModel>>,
                    response: Response<ArrayList<ItemsModel>>
                ) {
                    listFollowing.postValue(response.body())
                }

                override fun onFailure(call: Call<ArrayList<ItemsModel>>, t: Throwable) {
                    Log.d("ERROR", t.message.toString())
                }
            })

    }

    fun getListFollowing(): LiveData<ArrayList<ItemsModel>> {
        return listFollowing
    }
}