package com.example.hairbookfront.data.remote.ApiServices

import com.example.hairbookfront.domain.entities.BarberDTO
import com.example.hairbookfront.domain.entities.BarberShop
import com.example.hairbookfront.domain.entities.BarberSignUpRequest
import com.example.hairbookfront.domain.entities.Booking
import com.example.hairbookfront.domain.entities.CustomerDTO
import com.example.hairbookfront.domain.entities.CustomerSignUpRequest
import com.example.hairbookfront.domain.entities.LoginRequest
import com.example.hairbookfront.domain.entities.Review
import com.example.hairbookfront.domain.entities.Service
import com.example.hairbookfront.domain.entities.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiServiceAuth {
    @POST("/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<User>

    @POST("auth/sign-out")
    suspend fun signOut(
        @Header("Authorization") authToken: String
    ): Response<String>

    @GET("auth/get-details")
    suspend fun getDetailsCustomer(
        @Header("Authorization") authToken: String
    ): Response<CustomerDTO>

    @GET("auth/get-details")
    suspend fun getDetailsBarber(
        @Header("Authorization") authToken: String
    ): Response<BarberDTO>

    @POST("auth/sign-up")
    suspend fun signUpCustomer(
        @Body customerSignUpRequest: CustomerSignUpRequest
    ): Response<User>

    @POST("auth/sign-up")
    suspend fun signUpBarber(
        @Body barberSignUpRequest: BarberSignUpRequest
    ): Response<User>

}