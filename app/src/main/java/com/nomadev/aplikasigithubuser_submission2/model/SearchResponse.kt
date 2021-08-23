package com.nomadev.aplikasigithubuser_submission2.model

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("items")
    val items: ArrayList<ItemsModel>
)
