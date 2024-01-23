package com.example.hairbookfront.data.remote

import com.example.hairbookfront.domain.entities.BarberShop
import com.example.hairbookfront.domain.entities.Booking
import com.example.hairbookfront.domain.entities.LoginRequest
import com.example.hairbookfront.domain.entities.Review
import com.example.hairbookfront.domain.entities.Service
import com.example.hairbookfront.domain.entities.User
import com.example.hairbookfront.domain.entities.UserSignUpRequest
import retrofit2.Response
import javax.inject.Inject

class HairBookDataSourceImpl @Inject constructor(
    private val apiServiceAuth: ApiServiceAuth,
    private val apiServiceBarber: ApiServiceBarber,
    private val apiServiceBooking: ApiServiceBooking
) : HairBookDataSource {

    override suspend fun login(email: String, password: String): Response<User> {
        val loginRequest = LoginRequest(email, password)
        return apiServiceAuth.login(loginRequest)
    }

    override suspend fun getAllShops(accessToken: String): Response<List<BarberShop>> {
        return apiServiceAuth.getAllShops(accessToken)
    }

    override suspend fun signUp(signUpRequest: UserSignUpRequest): Response<User> {
        return apiServiceAuth.signUp(signUpRequest)
    }

    override suspend fun getMyBarberShops(accessToken: String): Response<List<BarberShop>> {
        return apiServiceBarber.getMyBarberShops(accessToken)
    }

    override suspend fun getBarberDetails(accessToken: String): Response<User> {
        return apiServiceBarber.getBarberDetails(accessToken)
    }

    override suspend fun createBarberShop(barberShop: BarberShop): Response<BarberShop> {
        return apiServiceBarber.createBarberShop(barberShop)
    }

    override suspend fun getBarberShopById(accessToken: String, barberShopId: String): Response<BarberShop> {
        return apiServiceBarber.getBarberShopById(accessToken, barberShopId)
    }

    override suspend fun deleteBarberShop(accessToken: String, barberShopId: String): Response<String> {
        return apiServiceBarber.deleteBarberShop(accessToken, barberShopId)
    }

    override suspend fun updateBarberShop(accessToken: String, barberShopId: String, barberShop: BarberShop): Response<BarberShop> {
        return apiServiceBarber.updateBarberShop(accessToken, barberShopId, barberShop)
    }

    override suspend fun getReviews(accessToken: String, barberShopId: String): Response<List<Review>> {
        return apiServiceBarber.getReviews(accessToken, barberShopId)
    }

    override suspend fun getClosestBooking(accessToken: String, barberShopId: String): Response<Booking> {
        return apiServiceBarber.getClosestBooking(accessToken, barberShopId)
    }

    override suspend fun getMyBookings(accessToken: String, barberShopId: String): Response<List<Booking>> {
        return apiServiceBarber.getMyBookings(accessToken, barberShopId)
    }

    override suspend fun createService(accessToken: String, barberShopId: String, service: Service): Response<Service> {
        return apiServiceBarber.createService(accessToken, barberShopId, service)
    }

    override suspend fun updateService(accessToken: String, barberShopId: String, serviceId: String, service: Service): Response<Service> {
        return apiServiceBarber.updateService(accessToken, barberShopId, serviceId, service)
    }

    override suspend fun deleteService(accessToken: String, barberShopId: String, serviceId: String): Response<String> {
        return apiServiceBarber.deleteService(accessToken, barberShopId, serviceId)
    }

    override suspend fun getServices(accessToken: String, barberShopId: String): Response<List<Service>> {
        return apiServiceBarber.getServices(accessToken, barberShopId)
    }

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