package com.example.sequence2.api

import com.example.sequence2.model.responses.*
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface Service {

    @POST("authenticate")
    suspend fun authenticate(@Query("user") user: String, @Query("password") pass: String): AuthResponse

    @GET("lists")
    suspend fun getLists(@Query("hash") hash: String): GetListsResponse

    @GET("lists/{id}/items")
    suspend fun getItems(@Path("id") id: Int, @Query("hash") hash: String): GetItemsResponse

    @POST("lists/{id}/items")
    suspend fun addItem(@Path("id") id: Int, @Query("hash") hash: String, @Query("label") description: String): AddItemResponse
}