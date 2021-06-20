package com.example.sequence3.data.source.remote.api.responses

import com.example.sequence3.data.model.ItemToDo
import com.google.gson.annotations.SerializedName

data class GetItemsResponse(
    @SerializedName("version")
    val version: String,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("status")
    val status: Int,
    @SerializedName("items")
    val items: MutableList<ItemToDo> = mutableListOf<ItemToDo>()
)