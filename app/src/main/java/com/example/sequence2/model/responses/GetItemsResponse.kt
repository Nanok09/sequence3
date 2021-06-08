package com.example.sequence2.model.responses

import com.example.sequence2.model.ItemToDo
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