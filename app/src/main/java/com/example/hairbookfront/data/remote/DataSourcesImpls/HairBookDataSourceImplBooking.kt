package com.example.hairbookfront.data.remote.DataSourcesImpls

import com.example.hairbookfront.data.remote.ApiServices.ApiServiceBooking
import com.example.hairbookfront.data.remote.DataSources.HairBookDataSourceBooking
import com.example.hairbookfront.domain.entities.Booking
import retrofit2.Response
import javax.inject.Inject

class HairBookDataSourceImplBooking @Inject constructor(
    private val apiServiceBooking: ApiServiceBooking
) : HairBookDataSourceBooking {
    override suspend fun bookHaircut(accessToken: String, booking: Booking): Response<Booking> {
        return apiServiceBooking.bookHaircut(accessToken,booking)
    }

    override suspend fun updateBooking(accessToken: String, bookingId: String, booking: Booking): Response<Booking> {
        return apiServiceBooking.updateBooking(accessToken, bookingId, booking)
    }

    override suspend fun deleteBooking(accessToken: String, bookingId: String): Response<String> {
        return apiServiceBooking.deleteBooking(accessToken, bookingId)
    }


    override suspend fun getUserBookings(accessToken: String): Response<List<Booking>> {
        return apiServiceBooking.getUserBookings(accessToken)
    }

    override suspend fun getClosestBooking(accessToken: String): Response<Booking> {
        return apiServiceBooking.getClosestBooking(accessToken)
    }
}