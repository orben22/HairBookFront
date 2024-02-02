package com.example.hairbookfront.data.remote.ApiServices

import com.example.hairbookfront.domain.entities.Booking
import com.example.hairbookfront.domain.entities.Service
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

    @GET("booking/get-service-by-id")
    suspend fun getServiceBookings(
        @Header("Authorization") authToken: String,
        @Query("serviceId") serviceId: String,
    ): Response<Service>

    @GET("booking/get-all-services-by-barbershop")
    suspend fun getAllServicesByBarberShop(
        @Header("Authorization") authToken: String,
        @Query("barberShopId") barberShopId: String,
    ): Response<List<Service>>

    @GET("booking/get-available-booking-by-day")
    suspend fun getAvailableBookingByDay(
        @Header("Authorization") authToken: String,
        @Query("barberShopId") barberShopId: String,
        @Query("date") date: String,
    ): Response<List<Boolean>>

    @GET("booking/get-booking-by-id")
    suspend fun getBookingById(
        @Header("Authorization") authToken: String,
        @Query("bookingId") bookingId: String,
    ): Response<Booking>
}