package com.example.sequence2.api

import com.example.sequence2.model.responses.AuthResponse
import retrofit2.http.POST
import retrofit2.http.Query


interface Service {

    @POST("authenticate")
    suspend fun authenticate(@Query("user") user: String, @Query("password") pass: String): AuthResponse
}