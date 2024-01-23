package com.example.hairbookfront.data.remote

import com.example.hairbookfront.domain.entities.BarberShop
import com.example.hairbookfront.domain.entities.LoginRequest
import com.example.hairbookfront.domain.entities.User
import com.example.hairbookfront.domain.entities.UserSignUpRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<User>

    @GET("/customer/get-all-shops")
    suspend fun getAllShops(
        @Header("Authorization") authToken: String
    ): Response<List<BarberShop>>

    @POST("auth/sign-up")
    suspend fun signUp(@Body userSignUpRequest: UserSignUpRequest): Response<User>

    @GET("barber/get-my-barbershops")
    suspend fun getMyBarberShops(
        @Header("Authorization") authToken: String
    ): Response<List<BarberShop>>


}