package com.example.hairbookfront.data.remote.DataSources

import com.example.hairbookfront.domain.entities.Booking
import com.example.hairbookfront.domain.entities.Service
import retrofit2.Response

interface HairBookDataSourceBooking {
    suspend fun bookHaircut(accessToken: String, booking: Booking): Response<Booking>

    suspend fun updateBooking(
        accessToken: String,
        bookingId: String,
        booking: Booking
    ): Response<Booking>

    suspend fun deleteBooking(accessToken: String, bookingId: String): Response<String>

    suspend fun getUserBookings(accessToken: String): Response<List<Booking>>

    suspend fun getClosestBooking(accessToken: String): Response<Booking>

    suspend fun getServiceBookings(accessToken: String, serviceId: String): Response<Service>

    suspend fun getAllServicesByBarberShop(accessToken: String, barberShopId: String): Response<List<Service>>

    suspend fun getAvailableBookingByDay(
        accessToken: String,
        barberShopId: String,
        date: String
    ): Response<List<Boolean>>

    suspend fun getBookingById(accessToken:String, bookingId:String):Response<Booking>
}