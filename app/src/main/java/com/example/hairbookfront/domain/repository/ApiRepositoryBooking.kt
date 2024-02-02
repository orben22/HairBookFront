package com.example.hairbookfront.domain.repository

import com.example.hairbookfront.data.remote.DataSources.HairBookDataSourceBooking
import com.example.hairbookfront.domain.entities.Booking
import com.example.hairbookfront.domain.entities.Service
import com.example.hairbookfront.util.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApiRepositoryBooking @Inject constructor(
    private val hairBookDataSourceBooking: HairBookDataSourceBooking
) {
    suspend fun bookHaircut(
        accessToken: String,
        booking: Booking
    ): Flow<ResourceState<Booking>> {
        return flow {
            emit(ResourceState.LOADING())
            val response = hairBookDataSourceBooking.bookHaircut(accessToken, booking)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.SUCCESS(response.body()!!))
            } else {
                emit(ResourceState.ERROR("Error booking haircut"))
            }
        }.catch { e ->
            emit(ResourceState.ERROR(e.localizedMessage ?: "Something went wrong with api"))
        }
    }

    suspend fun updateBooking(
        accessToken: String,
        bookingId: String,
        booking: Booking
    ): Flow<ResourceState<Booking>> {
        return flow {
            emit(ResourceState.LOADING())
            val response = hairBookDataSourceBooking.updateBooking(accessToken, bookingId, booking)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.SUCCESS(response.body()!!))
            } else {
                emit(ResourceState.ERROR("Error updating booking"))
            }
        }.catch { e ->
            emit(ResourceState.ERROR(e.localizedMessage ?: "Something went wrong with api"))
        }
    }

    suspend fun deleteBooking(
        accessToken: String,
        bookingId: String
    ): Flow<ResourceState<String>> {
        return flow {
            emit(ResourceState.LOADING())
            val response = hairBookDataSourceBooking.deleteBooking(accessToken, bookingId)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.SUCCESS(response.body()!!))
            } else {
                emit(ResourceState.ERROR("Error deleting booking"))
            }
        }.catch { e ->
            emit(ResourceState.ERROR(e.localizedMessage ?: "Something went wrong with api"))
        }
    }

    suspend fun getUserBookings(
        accessToken: String
    ): Flow<ResourceState<List<Booking>>> {
        return flow {
            emit(ResourceState.LOADING())
            val response = hairBookDataSourceBooking.getUserBookings(accessToken)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.SUCCESS(response.body()!!))
            } else {
                emit(ResourceState.ERROR("Error getting user bookings"))
            }
        }.catch { e ->
            emit(ResourceState.ERROR(e.localizedMessage ?: "Something went wrong with api"))
        }
    }

    suspend fun getClosestBooking(
        accessToken: String
    ): Flow<ResourceState<Booking>> {
        return flow {
            emit(ResourceState.LOADING())
            val response = hairBookDataSourceBooking.getClosestBooking(accessToken)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.SUCCESS(response.body()!!))
            } else {
                emit(ResourceState.ERROR("Error getting closest booking"))
            }
        }.catch { e ->
            emit(ResourceState.ERROR(e.localizedMessage ?: "Something went wrong with api"))
        }
    }

    suspend fun getServiceBookings(
        accessToken: String,
        serviceId: String
    ): Flow<ResourceState<Service>> {
        return flow {
            emit(ResourceState.LOADING())
            val response = hairBookDataSourceBooking.getServiceBookings(accessToken, serviceId)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.SUCCESS(response.body()!!))
            } else {
                emit(ResourceState.ERROR("Error getting service bookings"))
            }
        }.catch { e ->
            emit(ResourceState.ERROR(e.localizedMessage ?: "Something went wrong with api"))
        }
    }

    suspend fun getAllServicesByBarberShop(
        accessToken: String,
        barberShopId: String
    ): Flow<ResourceState<List<Service>>> {
        return flow {
            emit(ResourceState.LOADING())
            val response =
                hairBookDataSourceBooking.getAllServicesByBarberShop(accessToken, barberShopId)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.SUCCESS(response.body()!!))
            } else {
                emit(ResourceState.ERROR("Error getting all services by barber shop"))
            }
        }.catch { e ->
            emit(ResourceState.ERROR(e.localizedMessage ?: "Something went wrong with api"))
        }
    }

    suspend fun getAvailableBookingByDay(
        accessToken: String,
        barberShopId: String,
        date: String
    ): Flow<ResourceState<List<Boolean>>> {
        return flow {
            emit(ResourceState.LOADING())
            val response =
                hairBookDataSourceBooking.getAvailableBookingByDay(accessToken, barberShopId, date)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.SUCCESS(response.body()!!))
            } else {
                emit(ResourceState.ERROR("Error getting available booking by day"))
            }
        }.catch { e ->
            emit(ResourceState.ERROR(e.localizedMessage ?: "Something went wrong with api"))
        }
    }

    suspend fun getBookingById(
        accessToken: String,
        bookingId: String
    ): Flow<ResourceState<Booking>> {
        return flow {
            emit(ResourceState.LOADING())
            val response = hairBookDataSourceBooking.getBookingById(accessToken, bookingId)

            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.SUCCESS(response.body()!!))
            } else {
                emit(ResourceState.ERROR("Error getting booking by id"))
            }
        }.catch { e ->
            emit(ResourceState.ERROR(e.localizedMessage ?: "Something went wrong with api"))
        }
    }
}