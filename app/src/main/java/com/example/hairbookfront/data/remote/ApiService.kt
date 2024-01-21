package com.example.hairbookfront.data.remote

import com.example.hairbookfront.domain.entities.HairBookResponse
import com.example.hairbookfront.domain.entities.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<HairBookResponse>

    @POST("/auth/sign-up")
    suspend fun signUp(@Body str:String): Response<HairBookResponse>


}