package com.example.hairbookfront.domain.repository

import com.example.hairbookfront.data.remote.DataSources.HairBookDataSourceBarber
import com.example.hairbookfront.domain.entities.BarberDTO
import com.example.hairbookfront.domain.entities.BarberShop
import com.example.hairbookfront.domain.entities.Booking
import com.example.hairbookfront.domain.entities.Review
import com.example.hairbookfront.domain.entities.Service
import com.example.hairbookfront.util.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApiRepositoryBarber @Inject constructor(
    private val hairBookDataSourceBarber: HairBookDataSourceBarber
) {

    suspend fun getMyBarberShops(
        accessToken: String
    ): Flow<ResourceState<List<BarberShop>>> {
        return flow {
            emit(ResourceState.LOADING())
            val response = hairBookDataSourceBarber.getMyBarberShops(accessToken)
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
    ): Flow<ResourceState<BarberDTO>> {
        return flow {
            emit(ResourceState.LOADING())
            val response = hairBookDataSourceBarber.getBarberDetails(accessToken)
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
        accessToken: String,
        barberShop: BarberShop
    ): Flow<ResourceState<BarberShop>> {
        return flow {
            emit(ResourceState.LOADING())
            val response = hairBookDataSourceBarber.createBarberShop(accessToken, barberShop)
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
            val response = hairBookDataSourceBarber.getBarberShopById(accessToken, barberShopId)
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
            val response = hairBookDataSourceBarber.deleteBarberShop(accessToken, barberShopId)
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
            val response =
                hairBookDataSourceBarber.updateBarberShop(accessToken, barberShopId, barberShop)
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
            val response = hairBookDataSourceBarber.getReviews(accessToken, barberShopId)
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
            val response = hairBookDataSourceBarber.getClosestBooking(accessToken, barberShopId)
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
            val response = hairBookDataSourceBarber.getMyBookings(accessToken, barberShopId)
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
        service: Service,
    ): Flow<ResourceState<Service>> {
        return flow {
            emit(ResourceState.LOADING())
            val response =
                hairBookDataSourceBarber.createService(accessToken, barberShopId, service)
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
            val response = hairBookDataSourceBarber.updateService(
                accessToken,
                barberShopId,
                serviceId,
                service
            )
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
            val response =
                hairBookDataSourceBarber.deleteService(accessToken, barberShopId, serviceId)
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
            val response = hairBookDataSourceBarber.getServices(accessToken, barberShopId)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.SUCCESS(response.body()!!))
            } else {
                emit(ResourceState.ERROR("Error getting services"))
            }
        }.catch { e ->
            emit(ResourceState.ERROR(e.localizedMessage ?: "Something went wrong with api"))
        }
    }
}