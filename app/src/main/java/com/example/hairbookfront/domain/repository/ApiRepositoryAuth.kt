package com.example.hairbookfront.domain.repository

import com.example.hairbookfront.data.remote.dataSources.HairBookDataSourceAuth
import com.example.hairbookfront.domain.entities.BarberDTO
import com.example.hairbookfront.domain.entities.BarberSignUpRequest
import com.example.hairbookfront.domain.entities.CustomerDTO
import com.example.hairbookfront.domain.entities.CustomerSignUpRequest
import com.example.hairbookfront.domain.entities.User
import com.example.hairbookfront.util.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Repository for the authentication related operations.
 */
class ApiRepositoryAuth @Inject constructor(
    private val hairBookDataSourceAuth: HairBookDataSourceAuth
) {

    /**
     * Logs in the user.
     *
     * @param email The email of the user.
     * @param password The password of the user.
     * @return A flow of [ResourceState] of [User].
     */
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

    /**
     * Signs up a customer.
     *
     * @param customerSignUpRequest The customer sign up request.
     * @return A flow of [ResourceState] of [User].
     */
    suspend fun signUpCustomer(
        customerSignUpRequest: CustomerSignUpRequest
    ): Flow<ResourceState<User>> {
        return flow {
            emit(ResourceState.LOADING())
            val response = hairBookDataSourceAuth.signUpCustomer(customerSignUpRequest)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.SUCCESS(response.body()!!))
            } else {
                val errorMessage = if (response.code() == 400) {
                    response.errorBody()?.string()?.removeSurrounding("\"")
                } else {
                    response.toString()
                }
                emit(ResourceState.ERROR(errorMessage!!))
            }
        }.catch { e ->
            emit(ResourceState.ERROR(e.localizedMessage ?: "Something went wrong with api"))
        }
    }

    /**
     * Signs up a barber.
     *
     * @param barberSignUpRequest The barber sign up request.
     * @return A flow of [ResourceState] of [User].
     */
    suspend fun signUpBarber(
        barberSignUpRequest: BarberSignUpRequest
    ): Flow<ResourceState<User>> {
        return flow {
            emit(ResourceState.LOADING())
            val response = hairBookDataSourceAuth.signUpBarber(barberSignUpRequest)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.SUCCESS(response.body()!!))
            } else {
                val errorMessage = if (response.code() == 400) {
                    response.errorBody()?.string()?.removeSurrounding("\"")
                } else {
                    response.toString()
                }
                emit(ResourceState.ERROR(errorMessage!!))
            }
        }.catch { e ->
            emit(ResourceState.ERROR(e.localizedMessage ?: "Something went wrong with api"))
        }
    }

    /**
     * Signs out the user.
     *
     * @param accessToken The access token of the user.
     * @return A flow of [ResourceState] of [String].
     */
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

    /**
     * Gets the details of the barber.
     *
     * @param accessToken The access token of the user.
     * @return A flow of [ResourceState] of [BarberDTO].
     */
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

    /**
     * Gets the details of the customer.
     *
     * @param accessToken The access token of the user.
     * @return A flow of [ResourceState] of [CustomerDTO].
     */
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