package com.example.hairbookfront.data.remote.ApiServices

import com.example.hairbookfront.domain.entities.BarberShop
import com.example.hairbookfront.domain.entities.CustomerDTO
import com.example.hairbookfront.domain.entities.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiServiceCustomer {
    @GET("/customer/get-all-shops")
    suspend fun getAllShops(
        @Header("Authorization") authToken: String
    ): Response<List<BarberShop>>

    @GET("customer/get-customer-details")
    suspend fun getDetailsCustomer(
        @Header("Authorization") authToken: String
    ): Response<CustomerDTO>
}