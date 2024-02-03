package com.example.hairbookfront.domain.repository

import com.example.hairbookfront.data.remote.dataSources.HairBookDataSourceCustomer
import com.example.hairbookfront.domain.entities.BarberShop
import com.example.hairbookfront.domain.entities.CustomerDTO
import com.example.hairbookfront.util.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApiRepositoryCustomer @Inject constructor(
    private val hairBookDataSourceCustomer: HairBookDataSourceCustomer
) {
    /**
     * Gets all the barber shops.
     *
     * @param accessToken The access token of the user.
     * @return A flow of [ResourceState] of [List] of [BarberShop].
     */
    suspend fun getAllShops(
        accessToken: String
    ): Flow<ResourceState<List<BarberShop>>> {
        return flow {
            emit(ResourceState.LOADING())
            val response = hairBookDataSourceCustomer.getAllShops(accessToken)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.SUCCESS(response.body()!!))
            } else {
                emit(ResourceState.ERROR("Error, can't get barber shops"))
            }
        }.catch { e ->
            emit(ResourceState.ERROR(e.localizedMessage ?: "Something went wrong with api"))
        }
    }

    /**
     * Gets a barber shop by id.
     *
     * @param accessToken The access token of the user.
     * @param barbershopId The id of the barber shop.
     * @return A flow of [ResourceState] of [BarberShop].
     */
    suspend fun getShopById(
        accessToken: String,
        barbershopId: String
    ): Flow<ResourceState<BarberShop>> {
        return flow {
            emit(ResourceState.LOADING())
            val response = hairBookDataSourceCustomer.getShopById(accessToken, barbershopId)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.SUCCESS(response.body()!!))
            } else {
                emit(ResourceState.ERROR("Error, can't get barber shop by id"))
            }
        }.catch { e ->
            emit(ResourceState.ERROR(e.localizedMessage ?: "Something went wrong with api"))
        }
    }
}