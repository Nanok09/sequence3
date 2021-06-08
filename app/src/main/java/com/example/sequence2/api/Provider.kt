package com.example.sequence2.api

import com.example.sequence2.model.responses.*
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
    }

    suspend fun getLists(hash: String): GetListsResponse {
        return service.getLists(hash)
    }

    suspend fun getItems(id: Int, hash: String): GetItemsResponse {
        return service.getItems(id, hash)
    }

    suspend fun addItem(id: Int, hash: String, description: String): AddItemResponse {
        return service.addItem(id, hash, description)
    }

}