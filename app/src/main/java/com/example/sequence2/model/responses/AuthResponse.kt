package com.example.sequence2.model.responses

import com.google.gson.annotations.SerializedName

data class AuthResponse(

    @SerializedName("version")
    val version: String,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("status")
    val status: Int,
    @SerializedName("hash")
    val hash: String
)