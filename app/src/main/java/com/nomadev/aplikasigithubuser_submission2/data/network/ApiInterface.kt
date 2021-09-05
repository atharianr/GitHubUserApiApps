package com.nomadev.aplikasigithubuser_submission2.data.network

import com.nomadev.aplikasigithubuser_submission2.BuildConfig
import com.nomadev.aplikasigithubuser_submission2.domain.model.ItemsModel
import com.nomadev.aplikasigithubuser_submission2.domain.model.SearchResponse
import com.nomadev.aplikasigithubuser_submission2.domain.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("search/users")
    @Headers("Authorization: ${BuildConfig.GITHUB_TOKEN}")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<SearchResponse>

    @GET("users")
    @Headers("Authorization: ${BuildConfig.GITHUB_TOKEN}")
    fun getUserAll(
    ): Call<ArrayList<ItemsModel>>

    @GET("users/{username}")
    @Headers("Authorization: ${BuildConfig.GITHUB_TOKEN}")
    fun getUser(
        @Path("username") username: String
    ): Call<UserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: ${BuildConfig.GITHUB_TOKEN}")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<ArrayList<ItemsModel>>

    @GET("users/{username}/following")
    @Headers("Authorization: ${BuildConfig.GITHUB_TOKEN}")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<ArrayList<ItemsModel>>
}