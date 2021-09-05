package com.nomadev.aplikasigithubuser_submission2.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nomadev.aplikasigithubuser_submission2.domain.model.SearchResponse
import com.nomadev.aplikasigithubuser_submission2.domain.model.ItemsModel
import com.nomadev.aplikasigithubuser_submission2.data.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {

    val listUsers = MutableLiveData<ArrayList<ItemsModel>>()
    val status = MutableLiveData<Boolean>()

    fun setSearchUser(query: String) {
        RetrofitClient.apiInstance
            .getSearchUsers(query)
            .enqueue(object : Callback<SearchResponse> {
                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: Response<SearchResponse>
                ) {
                    if (response.isSuccessful) {
                        status.value = true
                        listUsers.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    status.value = false
                    Log.d("ERROR", t.message.toString())
                }
            })
    }

    fun getSearchUser(): LiveData<ArrayList<ItemsModel>>{
        return listUsers
    }
}