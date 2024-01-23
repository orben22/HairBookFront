package com.example.hairbookfront.data.remote.ApiServices

import com.example.hairbookfront.domain.entities.Booking
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiServiceBooking {
    @POST("booking/book-haircut")
    suspend fun bookHaircut(
        @Header("Authorization") authToken: String,
        @Body booking: Booking
    ): Response<Booking>


    @PUT("booking/update-booking")
    suspend fun updateBooking(
        @Header("Authorization") authToken: String,
        @Query("bookingId") bookingId: String,
        @Body booking: Booking
    ): Response<Booking>

    @DELETE("booking/delete-booking")
    suspend fun deleteBooking(
        @Header("Authorization") authToken: String,
        @Query("bookingId") bookingId: String,
    ): Response<String>

    @GET("booking/user-bookings")
    suspend fun getUserBookings(
        @Header("Authorization") authToken: String,
    ): Response<List<Booking>>

    @GET("booking/closest-booking")
    suspend fun getClosestBooking(
        @Header("Authorization") authToken: String,
    ): Response<Booking>

}