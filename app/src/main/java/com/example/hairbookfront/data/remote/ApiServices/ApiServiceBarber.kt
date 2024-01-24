package com.example.hairbookfront.data.remote.ApiServices

import com.example.hairbookfront.domain.entities.BarberDTO
import com.example.hairbookfront.domain.entities.BarberShop
import com.example.hairbookfront.domain.entities.Booking
import com.example.hairbookfront.domain.entities.Review
import com.example.hairbookfront.domain.entities.Service
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiServiceBarber {
    @GET("barber/get-my-barbershops")
    suspend fun getMyBarberShops(
        @Header("Authorization") authToken: String
    ): Response<List<BarberShop>>

    @GET("barber/get-barber-details")
    suspend fun getBarberDetails(
        @Header("Authorization") authToken: String
    ): Response<BarberDTO>

    @POST("barber/create-barbershop")
    suspend fun createBarberShop(
        @Body barberShop: BarberShop
    ): Response<BarberShop>

    @GET("barber/get-barbershop-by-id")
    suspend fun getBarberShopById(
        @Header("Authorization") authToken: String,
        @Query("barberShopId") barberShopId: String
    ): Response<BarberShop>

    @DELETE("barber/delete-barbershop")
    suspend fun deleteBarberShop(
        @Header("Authorization") authToken: String,
        @Query("barberShopId") barberShopId: String
    ): Response<String>

    @PUT("barber/update-barbershop")
    suspend fun updateBarberShop(
        @Header("Authorization") authToken: String,
        @Query("barberShopId") barberShopId: String,
        @Body barberShop: BarberShop
    ): Response<BarberShop>

    @GET("barber/get-reviews")
    suspend fun getReviews(
        @Header("Authorization") authToken: String,
        @Query("barberId") barberId: String
    ): Response<List<Review>>

    @GET("barber/get-closest-booking")
    suspend fun getClosestBooking(
        @Header("Authorization") authToken: String,
        @Query("barberShopId") barberShopId: String
    ): Response<Booking>

    @GET("barber/my-bookings")
    suspend fun getMyBookings(
        @Header("Authorization") authToken: String,
        @Query("barberShopId") barberShopId: String
    ): Response<List<Booking>>

    @POST("barber/create-service")
    suspend fun createService(
        @Header("Authorization") authToken: String,
        @Query("barberShopId") barberShopId: String,
        @Body service: Service
    ): Response<Service>

    @PUT("barber/update-service")
    suspend fun updateService(
        @Header("Authorization") authToken: String,
        @Query("barberShopId") barberShopId: String,
        @Query("serviceId") serviceId: String,
        @Body service: Service
    ): Response<Service>

    @DELETE("barber/delete-service")
    suspend fun deleteService(
        @Header("Authorization") authToken: String,
        @Query("barberShopId") barberShopId: String,
        @Query("serviceId") serviceId: String
    ): Response<String>

    @GET("barber/get-services")
    suspend fun getServices(
        @Header("Authorization") authToken: String,
        @Query("barberShopId") barberShopId: String
    ): Response<List<Service>>



}