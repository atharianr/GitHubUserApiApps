package com.nomadev.aplikasigithubuser_submission2.ui.detailuser.follow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nomadev.aplikasigithubuser_submission2.data.network.RetrofitClient
import com.nomadev.aplikasigithubuser_submission2.domain.model.ItemsModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {

    val listFollowers = MutableLiveData<ArrayList<ItemsModel>>()

    fun setListFollowers(username: String) {
        RetrofitClient.apiInstance
            .getUserFollowers(username)
            .enqueue(object : Callback<ArrayList<ItemsModel>> {
                override fun onResponse(
                    call: Call<ArrayList<ItemsModel>>,
                    response: Response<ArrayList<ItemsModel>>
                ) {
                    listFollowers.postValue(response.body())
                }

                override fun onFailure(call: Call<ArrayList<ItemsModel>>, t: Throwable) {
                    Log.d("ERROR", t.message.toString())
                }
            })
    }

    fun getListFollowers(): LiveData<ArrayList<ItemsModel>> = listFollowers
}