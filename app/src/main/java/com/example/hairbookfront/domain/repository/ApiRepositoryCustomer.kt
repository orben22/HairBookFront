package com.example.hairbookfront.domain.repository

import com.example.hairbookfront.data.remote.DataSources.HairBookDataSourceBooking
import com.example.hairbookfront.data.remote.DataSources.HairBookDataSourceCustomer
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

    suspend fun getDetailsCustomer(
        accessToken: String
    ): Flow<ResourceState<CustomerDTO>> {
        return flow {
            emit(ResourceState.LOADING())
            val response = hairBookDataSourceCustomer.getDetailsCustomer(accessToken)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.SUCCESS(response.body()!!))
            } else {
                emit(ResourceState.ERROR("Error, can't get details customer"))
            }
        }.catch { e ->
            emit(ResourceState.ERROR(e.localizedMessage ?: "Something went wrong with api"))
        }
    }
}