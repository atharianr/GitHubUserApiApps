package com.nomadev.aplikasigithubuser_submission2.model

import com.google.gson.annotations.SerializedName

data class ItemsModel(
    @SerializedName("login")
    val login: String,

    @SerializedName("id")
    val id: Int,

    @SerializedName("avatar_url")
    val avatar_url: String,

    @SerializedName("html_url")
    val html_url: String
)
