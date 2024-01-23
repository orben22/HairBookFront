package com.example.hairbookfront.data.remote

import com.example.hairbookfront.domain.entities.BarberDTO
import com.example.hairbookfront.domain.entities.BarberShop
import com.example.hairbookfront.domain.entities.Booking
import com.example.hairbookfront.domain.entities.CustomerDTO
import com.example.hairbookfront.domain.entities.Review
import com.example.hairbookfront.domain.entities.Service
import com.example.hairbookfront.domain.entities.User
import com.example.hairbookfront.domain.entities.UserSignUpRequest
import retrofit2.Response
import retrofit2.http.Query

interface HairBookDataSource {
    suspend fun login(email: String, password: String): Response<User>
    suspend fun signOut(accessToken: String): Response<String>
    suspend fun getAllShops(accessToken: String): Response<List<BarberShop>>

    suspend fun signUp(signUpRequest: UserSignUpRequest): Response<User>

    suspend fun getDetailsCustomer(accessToken: String): Response<CustomerDTO>

    suspend fun getDetailsBarber(accessToken: String): Response<BarberDTO>

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

    suspend fun bookHaircut(accessToken: String, booking: Booking): Response<Booking>

    suspend fun updateBooking(accessToken: String, bookingId: String, booking: Booking): Response<Booking>

    suspend fun deleteBooking(accessToken: String, bookingId: String): Response<String>

    suspend fun getUserBookings(accessToken: String): Response<List<Booking>>

    suspend fun getClosestBooking(accessToken: String): Response<Booking>

    suspend fun postReview(accessToken: String, review: Review): Response<Review>

    suspend fun deleteReview(accessToken: String, reviewId: String): Response<String>
}