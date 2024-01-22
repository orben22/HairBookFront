package com.example.hairbookfront.domain.repository

import com.example.hairbookfront.data.remote.HairBookDataSource
import com.example.hairbookfront.domain.entities.HairBookResponse
import com.example.hairbookfront.util.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApiRepository @Inject constructor(private val hairBookDataSource: HairBookDataSource) {

    suspend fun login(
        email: String,
        password: String
    ): Flow<ResourceState<HairBookResponse>> {
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
    ): Flow<ResourceState<HairBookResponse>> {
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
}