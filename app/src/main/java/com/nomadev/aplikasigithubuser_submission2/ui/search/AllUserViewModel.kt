package com.nomadev.aplikasigithubuser_submission2.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nomadev.aplikasigithubuser_submission2.data.network.RetrofitClient
import com.nomadev.aplikasigithubuser_submission2.domain.model.ItemsModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllUserViewModel : ViewModel() {

    private val listAllUser = MutableLiveData<ArrayList<ItemsModel>>()
    val status = MutableLiveData<Boolean>()

    fun setListAllUser() {
        RetrofitClient.apiInstance
            .getUserAll()
            .enqueue(object : Callback<ArrayList<ItemsModel>> {
                override fun onResponse(
                    call: Call<ArrayList<ItemsModel>>,
                    response: Response<ArrayList<ItemsModel>>
                ) {
                    status.value = true
                    listAllUser.postValue(response.body())
                    Log.d("STATUS", status.value.toString())
                }

                override fun onFailure(call: Call<ArrayList<ItemsModel>>, t: Throwable) {
                    status.value = false
                    Log.d("ERROR", t.message.toString())
                    Log.d("STATUS", status.value.toString())
                }
            })
    }

    fun getListAllUser(): LiveData<ArrayList<ItemsModel>> = listAllUser
}