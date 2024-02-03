package com.example.hairbookfront.domain.repository

import com.example.hairbookfront.data.remote.dataSources.HairBookDataSourceBarber
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

/**
 * Repository for the barber related operations.
 */
class ApiRepositoryBarber @Inject constructor(
    private val hairBookDataSourceBarber: HairBookDataSourceBarber
) {

    /**
     * Gets the barber shops of the user.
     *
     * @param accessToken The access token of the user.
     * @return A flow of [ResourceState] of a list of [BarberShop].
     */
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

    /**
     * Creates a barber shop.
     *
     * @param accessToken The access token of the user.
     * @param barberShop The barber shop to be created.
     * @return A flow of [ResourceState] of [BarberShop].
     */
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

    /**
     * Gets a barber shop by its id.
     *
     * @param accessToken The access token of the user.
     * @param barberShopId The id of the barber shop.
     * @return A flow of [ResourceState] of [BarberShop].
     */
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

    /**
     * Deletes a barber shop.
     *
     * @param accessToken The access token of the user.
     * @param barberShopId The id of the barber shop.
     * @return A flow of [ResourceState] of [String].
     */
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

    /**
     * Updates a barber shop.
     *
     * @param accessToken The access token of the user.
     * @param barberShopId The id of the barber shop.
     * @param barberShop The updated barber shop details.
     * @return A flow of [ResourceState] of [BarberShop].
     */
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

    /**
     * Creates a review.
     *
     * @param accessToken The access token of the user.
     * @param review The review to be created.
     * @return A flow of [ResourceState] of [Review].
     */
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

    /**
     * Gets the closest booking of the authenticated user.
     *
     * @param accessToken The authorization token of the user.
     * @return A flow of [ResourceState] of [Booking].
     */
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

    /**
     * Gets the bookings of the authenticated user.
     *
     * @param accessToken The authorization token of the user.
     * @return A flow of [ResourceState] of a list of [Booking].
     */
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

    /**
     * Creates a service.
     *
     * @param accessToken The access token of the user.
     * @param barberShopId The id of the barber shop.
     * @param service The service to be created.
     * @return A flow of [ResourceState] of [Service].
     */
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

    /**
     * Updates a service.
     *
     * @param accessToken The access token of the user.
     * @param barberShopId The id of the barber shop.
     * @param serviceId The id of the service.
     * @param service The updated service details.
     * @return A flow of [ResourceState] of [Service].
     */
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

    /**
     * Deletes a service.
     *
     * @param accessToken The access token of the user.
     * @param barberShopId The id of the barber shop.
     * @param serviceId The id of the service.
     * @return A flow of [ResourceState] of [String].
     */
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

    /**
     * Gets the services of a barber shop.
     *
     * @param accessToken The access token of the user.
     * @param barberShopId The id of the barber shop.
     * @return A flow of [ResourceState] of a list of [Service].
     */
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

    /**
     * Deletes a booking.
     * @param accessToken The access token of the user.
     * @param barberShopId The id of the barber shop the booking is on.
     * @param bookingId The id of the booking to be deleted.
     * @return A flow of [ResourceState] of [String].
     */
    suspend fun deleteBooking(
        accessToken: String,
        barberShopId: String,
        bookingId: String
    ): Flow<ResourceState<String>> {
        return flow {
            emit(ResourceState.LOADING())
            val response =
                hairBookDataSourceBarber.deleteBooking(accessToken, barberShopId, bookingId)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.SUCCESS(response.body()!!))
            } else {
                emit(ResourceState.ERROR("Error deleting booking"))
            }
        }.catch { e ->
            emit(ResourceState.ERROR(e.localizedMessage ?: "Something went wrong with api"))
        }
    }

    /**
     * get the number of shops the barber have.
     * @param accessToken The access token of the user.
     * @return A flow of [ResourceState] of [Int].
     */
    suspend fun getNumberOfShops(
        accessToken: String
    ): Flow<ResourceState<Int>> {
        return flow {
            emit(ResourceState.LOADING())
            val response = hairBookDataSourceBarber.getNumberOfShops(accessToken)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.SUCCESS(response.body()!!))
            } else {
                emit(ResourceState.ERROR("Error getting number of shops"))
            }
        }.catch { e ->
            emit(ResourceState.ERROR(e.localizedMessage ?: "Something went wrong with api"))
        }
    }
}