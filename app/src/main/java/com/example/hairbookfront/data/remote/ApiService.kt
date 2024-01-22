package com.example.hairbookfront.data.remote

import com.example.hairbookfront.domain.entities.HairBookResponse
import com.example.hairbookfront.domain.entities.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<HairBookResponse>

    @GET("/customer/get-all-shops")
    suspend fun getAllShops(
        @Header("Authorization") authToken: String
    ): Response<HairBookResponse>


}