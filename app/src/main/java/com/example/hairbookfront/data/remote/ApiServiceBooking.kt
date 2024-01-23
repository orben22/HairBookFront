package com.example.hairbookfront.data.remote

import com.example.hairbookfront.domain.entities.BarberShop
import com.example.hairbookfront.domain.entities.LoginRequest
import com.example.hairbookfront.domain.entities.User
import com.example.hairbookfront.domain.entities.UserSignUpRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiServiceBooking {
    @POST("barber/book-haircut")
    suspend fun bookHaircut(
        @Header("Authorization") authToken: String,
        @Body barberShopId: Long
    ): Response<Unit>


    @PUT("barber/update-booking")
    suspend fun updateBooking(
        @Header("Authorization") authToken: String,
        @Query("bookingId") bookingId: String,
    ): Response<Unit>

    @DELETE("barber/delete-booking")
    suspend fun deleteBooking(
        @Header("Authorization") authToken: String,
        @Query("bookingId") bookingId: String,
    ): Response<Unit>

    @GET("barber/user-bookings")
    suspend fun getUserBookings(
        @Header("Authorization") authToken: String,
    ): Response<List<BarberShop>>

    @GET("barber/closest-booking")
    suspend fun getClosestBooking(
        @Header("Authorization") authToken: String,
    ): Response<BarberShop>

}