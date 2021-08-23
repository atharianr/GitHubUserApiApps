package com.nomadev.aplikasigithubuser_submission2.network

import com.nomadev.aplikasigithubuser_submission2.model.ItemsModel
import com.nomadev.aplikasigithubuser_submission2.model.SearchResponse
import com.nomadev.aplikasigithubuser_submission2.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<SearchResponse>

    @GET("users")
    @Headers("Authorization: token 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")
    fun getUserAll(
    ): Call<ArrayList<ItemsModel>>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUser(
        @Path("username") username: String
    ): Call<UserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<ArrayList<ItemsModel>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<ArrayList<ItemsModel>>
}