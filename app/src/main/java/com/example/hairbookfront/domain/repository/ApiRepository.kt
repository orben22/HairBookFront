package com.example.hairbookfront.domain.repository

import com.example.hairbookfront.data.remote.HairBookDataSource
import com.example.hairbookfront.domain.entities.BarberShop
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

    //start from here
    suspend fun bookHaircut(
        accessToken: String,
        barberShopId: Long
    ): Flow<ResourceState<Unit>> {
        return flow {
            emit(ResourceState.LOADING())
            val response = hairBookDataSource.bookHaircut(accessToken, barberShopId)
            if (response.isSuccessful) {
                emit(ResourceState.SUCCESS(Unit))
            } else {
                emit(ResourceState.ERROR("Error booking haircut"))
            }
        }.catch { e ->
            emit(ResourceState.ERROR(e.localizedMessage ?: "Something went wrong with api"))
        }
    }

    suspend fun updateBooking(
        accessToken: String,
        bookingId: String
    ): Flow<ResourceState<Unit>> {
        return flow {
            emit(ResourceState.LOADING())
            val response = hairBookDataSource.updateBooking(accessToken, bookingId)
            if (response.isSuccessful) {
                emit(ResourceState.SUCCESS(Unit))
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
    ): Flow<ResourceState<Unit>> {
        return flow {
            emit(ResourceState.LOADING())
            val response = hairBookDataSource.deleteBooking(accessToken, bookingId)
            if (response.isSuccessful) {
                emit(ResourceState.SUCCESS(Unit))
            } else {
                emit(ResourceState.ERROR("Error deleting booking"))
            }
        }.catch { e ->
            emit(ResourceState.ERROR(e.localizedMessage ?: "Something went wrong with api"))
        }
    }

    suspend fun getUserBookings(
        accessToken: String
    ): Flow<ResourceState<List<BarberShop>>> {
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
    ): Flow<ResourceState<BarberShop>> {
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