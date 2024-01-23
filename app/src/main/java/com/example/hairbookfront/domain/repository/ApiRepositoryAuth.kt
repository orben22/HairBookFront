package com.example.hairbookfront.domain.repository

import com.example.hairbookfront.data.remote.DataSources.HairBookDataSourceAuth
import com.example.hairbookfront.domain.entities.BarberDTO
import com.example.hairbookfront.domain.entities.BarberShop
import com.example.hairbookfront.domain.entities.CustomerDTO
import com.example.hairbookfront.domain.entities.User
import com.example.hairbookfront.domain.entities.UserSignUpRequest
import com.example.hairbookfront.util.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApiRepositoryAuth @Inject constructor(
    private val hairBookDataSourceAuth: HairBookDataSourceAuth
) {

    suspend fun login(
        email: String,
        password: String
    ): Flow<ResourceState<User>> {
        return flow {
            emit(ResourceState.LOADING())
            val response = hairBookDataSourceAuth.login(email, password)
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
            val response = hairBookDataSourceAuth.getAllShops(accessToken)
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
            val response = hairBookDataSourceAuth.signUp(signUpRequest)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.SUCCESS(response.body()!!))
            } else {
                emit(ResourceState.ERROR("Error Sign Up"))
            }
        }.catch { e ->
            emit(ResourceState.ERROR(e.localizedMessage ?: "Something went wrong with api"))
        }
    }

    suspend fun signOut(
        accessToken: String
    ): Flow<ResourceState<String>> {
        return flow {
            emit(ResourceState.LOADING())
            val response = hairBookDataSourceAuth.signOut(accessToken)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.SUCCESS(response.body()!!))
            } else {
                emit(ResourceState.ERROR("Sign out failed"))
            }
        }.catch { e ->
            emit(ResourceState.ERROR(e.localizedMessage ?: "Something went wrong with api"))
        }
    }
    suspend fun getDetailsBarber(
        accessToken: String
    ): Flow<ResourceState<BarberDTO>> {
        return flow {
            emit(ResourceState.LOADING())
            val response = hairBookDataSourceAuth.getDetailsBarber(accessToken)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.SUCCESS(response.body()!!))
            } else {
                emit(ResourceState.ERROR("Get details failed"))
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
            val response = hairBookDataSourceAuth.getDetailsCustomer(accessToken)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.SUCCESS(response.body()!!))
            } else {
                emit(ResourceState.ERROR("Get details failed"))
            }
        }.catch { e ->
            emit(ResourceState.ERROR(e.localizedMessage ?: "Something went wrong with api"))
        }
    }
}