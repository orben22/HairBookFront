package com.example.hairbookfront.domain.repository

import com.example.hairbookfront.data.remote.HairBookDataSource
import com.example.hairbookfront.domain.entities.BarberShop
import com.example.hairbookfront.domain.entities.Booking
import com.example.hairbookfront.domain.entities.Review
import com.example.hairbookfront.domain.entities.Service
import com.example.hairbookfront.domain.entities.User
import com.example.hairbookfront.domain.entities.UserSignUpRequest
import com.example.hairbookfront.util.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApiRepository @Inject constructor(private val hairBookDataSource: HairBookDataSource) {

    suspend fun login(
        email: String,
        password: String
    ): Flow<ResourceState<User>> {
        return flow {
            emit(ResourceState.LOADING())
            val response = hairBookDataSource.login(email, password)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.SUCCESS(response.body()!!))
            } else {
                emit(ResourceState.ERROR("Error Logging in user"))
            }
        }.catch { e ->
            emit(ResourceState.ERROR(e.localizedMessage ?: "Something went wrong with api"))
        }
    }

    suspend fun getAllShops(
        accessToken: String
    ): Flow<ResourceState<List<BarberShop>>> {
        return flow {
            emit(ResourceState.LOADING())
            val response = hairBookDataSource.getAllShops(accessToken)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.SUCCESS(response.body()!!))
            } else {
                emit(ResourceState.ERROR("Error Logging in user"))
            }
        }.catch { e ->
            emit(ResourceState.ERROR(e.localizedMessage ?: "Something went wrong with api"))
        }
    }

    suspend fun signUp(
        signUpRequest: UserSignUpRequest
    ): Flow<ResourceState<User>> {
        return flow {
            emit(ResourceState.LOADING())
            val response = hairBookDataSource.signUp(signUpRequest)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.SUCCESS(response.body()!!))
            } else {
                emit(ResourceState.ERROR("Error Sign Up"))
            }
        }.catch { e ->
            emit(ResourceState.ERROR(e.localizedMessage ?: "Something went wrong with api"))
        }
    }

suspend fun getMyBarberShops(
        accessToken: String
    ): Flow<ResourceState<List<BarberShop>>> {
        return flow {
            emit(ResourceState.LOADING())
            val response = hairBookDataSource.getMyBarberShops(accessToken)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.SUCCESS(response.body()!!))
            } else {
                emit(ResourceState.ERROR("Error getting barber shops"))
            }
        }.catch { e ->
            emit(ResourceState.ERROR(e.localizedMessage ?: "Something went wrong with api"))
        }
    }

    suspend fun getBarberDetails(
        accessToken: String
    ): Flow<ResourceState<User>> {
        return flow {
            emit(ResourceState.LOADING())
            val response = hairBookDataSource.getBarberDetails(accessToken)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.SUCCESS(response.body()!!))
            } else {
                emit(ResourceState.ERROR("Error getting barber details"))
            }
        }.catch { e ->
            emit(ResourceState.ERROR(e.localizedMessage ?: "Something went wrong with api"))
        }
    }

    suspend fun createBarberShop(
        barberShop: BarberShop
    ): Flow<ResourceState<BarberShop>> {
        return flow {
            emit(ResourceState.LOADING())
            val response = hairBookDataSource.createBarberShop(barberShop)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.SUCCESS(response.body()!!))
            } else {
                emit(ResourceState.ERROR("Error creating barber shop"))
            }
        }.catch { e ->
            emit(ResourceState.ERROR(e.localizedMessage ?: "Something went wrong with api"))
        }
    }

    suspend fun getBarberShopById(
        accessToken: String,
        barberShopId: String
    ): Flow<ResourceState<BarberShop>> {
        return flow {
            emit(ResourceState.LOADING())
            val response = hairBookDataSource.getBarberShopById(accessToken,barberShopId)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.SUCCESS(response.body()!!))
            } else {
                emit(ResourceState.ERROR("Error creating barber shop"))
            }
        }.catch { e ->
            emit(ResourceState.ERROR(e.localizedMessage ?: "Something went wrong with api"))
        }
    }

    suspend fun deleteBarberShop(
        accessToken: String,
        barberShopId: String
    ): Flow<ResourceState<String>> {
        return flow {
            emit(ResourceState.LOADING())
            val response = hairBookDataSource.deleteBarberShop(accessToken,barberShopId)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.SUCCESS(response.body()!!))
            } else {
                emit(ResourceState.ERROR("Error deleting barber shop"))
            }
        }.catch { e ->
            emit(ResourceState.ERROR(e.localizedMessage ?: "Something went wrong with api"))
        }
    }

    suspend fun updateBarberShop(
        accessToken: String,
        barberShopId: String,
        barberShop: BarberShop
    ): Flow<ResourceState<BarberShop>> {
        return flow {
            emit(ResourceState.LOADING())
            val response = hairBookDataSource.updateBarberShop(accessToken,barberShopId,barberShop)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.SUCCESS(response.body()!!))
            } else {
                emit(ResourceState.ERROR("Error updating barber shop"))
            }
        }.catch { e ->
            emit(ResourceState.ERROR(e.localizedMessage ?: "Something went wrong with api"))
        }
    }

    suspend fun getReviews(
        accessToken: String,
        barberShopId: String
    ): Flow<ResourceState<List<Review>>> {
        return flow {
            emit(ResourceState.LOADING())
            val response = hairBookDataSource.getReviews(accessToken,barberShopId)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.SUCCESS(response.body()!!))
            } else {
                emit(ResourceState.ERROR("Error getting reviews"))
            }
        }.catch { e ->
            emit(ResourceState.ERROR(e.localizedMessage ?: "Something went wrong with api"))
        }


    }

    suspend fun getClosestBooking(
        accessToken: String,
        barberShopId: String
    ): Flow<ResourceState<Booking>> {
        return flow {
            emit(ResourceState.LOADING())
            val response = hairBookDataSource.getClosestBooking(accessToken,barberShopId)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.SUCCESS(response.body()!!))
            } else {
                emit(ResourceState.ERROR("Error getting closest booking"))
            }
        }.catch { e ->
            emit(ResourceState.ERROR(e.localizedMessage ?: "Something went wrong with api"))
        }
    }

    suspend fun getMyBookings(
        accessToken: String,
        barberShopId: String
    ): Flow<ResourceState<List<Booking>>> {
        return flow {
            emit(ResourceState.LOADING())
            val response = hairBookDataSource.getMyBookings(accessToken,barberShopId)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.SUCCESS(response.body()!!))
            } else {
                emit(ResourceState.ERROR("Error getting my bookings"))
            }
        }.catch { e ->
            emit(ResourceState.ERROR(e.localizedMessage ?: "Something went wrong with api"))
        }
    }

    suspend fun createService(
        accessToken: String,
        barberShopId: String,
        service: Service
    ): Flow<ResourceState<Service>> {
        return flow {
            emit(ResourceState.LOADING())
            val response = hairBookDataSource.createService(accessToken,barberShopId,service)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.SUCCESS(response.body()!!))
            } else {
                emit(ResourceState.ERROR("Error creating service"))
            }
        }.catch { e ->
            emit(ResourceState.ERROR(e.localizedMessage ?: "Something went wrong with api"))
        }
    }

    suspend fun updateService(
        accessToken: String,
        barberShopId: String,
        serviceId: String,
        service: Service
    ): Flow<ResourceState<Service>> {
        return flow {
            emit(ResourceState.LOADING())
            val response = hairBookDataSource.updateService(accessToken,barberShopId,serviceId,service)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.SUCCESS(response.body()!!))
            } else {
                emit(ResourceState.ERROR("Error updating service"))
            }
        }.catch { e ->
            emit(ResourceState.ERROR(e.localizedMessage ?: "Something went wrong with api"))
        }
    }

suspend fun deleteService(
        accessToken: String,
        barberShopId: String,
        serviceId: String
    ): Flow<ResourceState<String>> {
        return flow {
            emit(ResourceState.LOADING())
            val response = hairBookDataSource.deleteService(accessToken,barberShopId,serviceId)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.SUCCESS(response.body()!!))
            } else {
                emit(ResourceState.ERROR("Error deleting service"))
            }
        }.catch { e ->
            emit(ResourceState.ERROR(e.localizedMessage ?: "Something went wrong with api"))
        }
    }

    suspend fun getServices(
        accessToken: String,
        barberShopId: String
    ): Flow<ResourceState<List<Service>>> {
        return flow {
            emit(ResourceState.LOADING())
            val response = hairBookDataSource.getServices(accessToken,barberShopId)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.SUCCESS(response.body()!!))
            } else {
                emit(ResourceState.ERROR("Error getting services"))
            }
        }.catch { e ->
            emit(ResourceState.ERROR(e.localizedMessage ?: "Something went wrong with api"))
        }
    }

    suspend fun bookHaircut(
        accessToken: String,
        booking: Booking
    ): Flow<ResourceState<Booking>> {
        return flow {
            emit(ResourceState.LOADING())
            val response = hairBookDataSource.bookHaircut(accessToken, booking)
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
            val response = hairBookDataSource.updateBooking(accessToken, bookingId, booking)
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
            val response = hairBookDataSource.deleteBooking(accessToken, bookingId)
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
            val response = hairBookDataSource.getUserBookings(accessToken)
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
            val response = hairBookDataSource.getClosestBooking(accessToken)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.SUCCESS(response.body()!!))
            } else {
                emit(ResourceState.ERROR("Error getting closest booking"))
            }
        }.catch { e ->
            emit(ResourceState.ERROR(e.localizedMessage ?: "Something went wrong with api"))
        }
    }

}