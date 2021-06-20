package com.example.sequence3.data.source.remote.api.responses

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