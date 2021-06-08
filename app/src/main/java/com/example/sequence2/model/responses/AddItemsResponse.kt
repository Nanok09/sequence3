package com.example.sequence2.model.responses

import com.example.sequence2.model.ItemToDo
import com.google.gson.annotations.SerializedName

data class AddItemResponse(
    @SerializedName("version")
    val version: String,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("status")
    val status: Int,
    @SerializedName("item")
    val item: ItemToDo = ItemToDo()
)