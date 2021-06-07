package com.example.sequence2.api

import com.example.sequence2.model.responses.AuthResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Provider {

    private val BASE_URL = "http://tomnab.fr/todo-api/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    private val service = retrofit.create(Service::class.java)


    suspend fun authenticate(user: String, pass: String): AuthResponse {
        return service.authenticate(user, pass)
    }}