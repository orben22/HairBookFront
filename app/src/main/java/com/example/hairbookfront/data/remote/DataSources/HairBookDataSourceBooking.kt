package com.example.hairbookfront.data.remote.DataSources

import com.example.hairbookfront.domain.entities.Booking
import retrofit2.Response

interface HairBookDataSourceBooking {
    suspend fun bookHaircut(accessToken: String, booking: Booking): Response<Booking>

    suspend fun updateBooking(accessToken: String, bookingId: String, booking: Booking): Response<Booking>

    suspend fun deleteBooking(accessToken: String, bookingId: String): Response<String>

    suspend fun getUserBookings(accessToken: String): Response<List<Booking>>

    suspend fun getClosestBooking(accessToken: String): Response<Booking>
}