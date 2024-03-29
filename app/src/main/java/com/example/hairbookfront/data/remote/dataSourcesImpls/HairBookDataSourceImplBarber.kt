package com.example.hairbookfront.data.remote.dataSourcesImpls

import com.example.hairbookfront.data.remote.apiServices.ApiServiceBarber
import com.example.hairbookfront.data.remote.dataSources.HairBookDataSourceBarber
import com.example.hairbookfront.domain.entities.BarberDTO
import com.example.hairbookfront.domain.entities.BarberShop
import com.example.hairbookfront.domain.entities.Booking
import com.example.hairbookfront.domain.entities.Review
import com.example.hairbookfront.domain.entities.Service
import retrofit2.Response
import javax.inject.Inject

/**
 * Implementation of the [HairBookDataSourceBarber] interface.
 */
class HairBookDataSourceImplBarber @Inject constructor(
    private val apiServiceBarber: ApiServiceBarber
) : HairBookDataSourceBarber {
    override suspend fun getMyBarberShops(accessToken: String): Response<List<BarberShop>> {
        return apiServiceBarber.getMyBarberShops("Bearer $accessToken")
    }


    override suspend fun createBarberShop(
        accessToken: String,
        barberShop: BarberShop
    ): Response<BarberShop> {
        return apiServiceBarber.createBarberShop("Bearer $accessToken", barberShop)
    }

    override suspend fun getBarberShopById(
        accessToken: String,
        barberShopId: String
    ): Response<BarberShop> {
        return apiServiceBarber.getBarberShopById("Bearer $accessToken", barberShopId)
    }

    override suspend fun deleteBarberShop(
        accessToken: String,
        barberShopId: String
    ): Response<String> {
        return apiServiceBarber.deleteBarberShop("Bearer $accessToken", barberShopId)
    }

    override suspend fun updateBarberShop(
        accessToken: String,
        barberShopId: String,
        barberShop: BarberShop
    ): Response<BarberShop> {
        return apiServiceBarber.updateBarberShop("Bearer $accessToken", barberShopId, barberShop)
    }

    override suspend fun getReviews(
        accessToken: String,
        barberShopId: String
    ): Response<List<Review>> {
        return apiServiceBarber.getReviews("Bearer $accessToken", barberShopId)
    }

    override suspend fun getClosestBooking(
        accessToken: String,
        barberShopId: String
    ): Response<Booking> {
        return apiServiceBarber.getClosestBooking("Bearer $accessToken", barberShopId)
    }

    override suspend fun getMyBookings(
        accessToken: String,
        barberShopId: String
    ): Response<List<Booking>> {
        return apiServiceBarber.getMyBookings("Bearer $accessToken", barberShopId)
    }

    override suspend fun createService(
        accessToken: String,
        barberShopId: String,
        service: Service
    ): Response<Service> {
        return apiServiceBarber.createService("Bearer $accessToken", barberShopId, service)
    }

    override suspend fun updateService(
        accessToken: String,
        barberShopId: String,
        serviceId: String,
        service: Service
    ): Response<Service> {
        return apiServiceBarber.updateService(
            "Bearer $accessToken",
            barberShopId,
            serviceId,
            service
        )
    }

    override suspend fun deleteService(
        accessToken: String,
        barberShopId: String,
        serviceId: String
    ): Response<String> {
        return apiServiceBarber.deleteService("Bearer $accessToken", barberShopId, serviceId)
    }

    override suspend fun getServices(
        accessToken: String,
        barberShopId: String
    ): Response<List<Service>> {
        return apiServiceBarber.getServices("Bearer $accessToken", barberShopId)
    }

    override suspend fun deleteBooking(
        accessToken: String,
        barberShopId: String,
        bookingId: String
    ): Response<String> {
        return apiServiceBarber.deleteBooking("Bearer $accessToken", barberShopId, bookingId)
    }

    override suspend fun getNumberOfShops(accessToken: String): Response<Int> {
        return apiServiceBarber.getNumberOfShops("Bearer $accessToken")
    }

    override suspend fun getBarberBookings(accessToken: String): Response<List<Booking>> {
        return apiServiceBarber.getBarberBookings("Bearer $accessToken")
    }
}