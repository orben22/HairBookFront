package com.example.hairbookfront.data.remote.DataSources

import com.example.hairbookfront.domain.entities.BarberShop
import com.example.hairbookfront.domain.entities.Booking
import com.example.hairbookfront.domain.entities.Review
import com.example.hairbookfront.domain.entities.Service
import com.example.hairbookfront.domain.entities.User
import retrofit2.Response

interface HairBookDataSourceBarber {
    suspend fun getMyBarberShops(accessToken: String): Response<List<BarberShop>>

    suspend fun getBarberDetails(accessToken: String): Response<User>

    suspend fun createBarberShop(barberShop: BarberShop): Response<BarberShop>

    suspend fun getBarberShopById(accessToken: String, barberShopId: String): Response<BarberShop>

    suspend fun deleteBarberShop(accessToken: String, barberShopId: String): Response<String>

    suspend fun updateBarberShop(accessToken: String, barberShopId: String, barberShop: BarberShop): Response<BarberShop>

    suspend fun getReviews(accessToken: String, barberShopId: String): Response<List<Review>>

    suspend fun getClosestBooking(accessToken: String, barberShopId: String): Response<Booking>

    suspend fun getMyBookings(accessToken: String, barberShopId: String): Response<List<Booking>>

    suspend fun createService(accessToken: String, barberShopId: String, service: Service): Response<Service>

    suspend fun updateService(accessToken: String, barberShopId: String, serviceId: String, service: Service): Response<Service>

    suspend fun deleteService(accessToken: String, barberShopId: String, serviceId: String): Response<String>

    suspend fun getServices(accessToken: String, barberShopId: String): Response<List<Service>>
}