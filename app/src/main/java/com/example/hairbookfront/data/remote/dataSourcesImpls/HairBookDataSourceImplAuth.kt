package com.example.hairbookfront.data.remote.dataSourcesImpls

import com.example.hairbookfront.data.remote.apiServices.ApiServiceAuth
import com.example.hairbookfront.data.remote.dataSources.HairBookDataSourceAuth
import com.example.hairbookfront.domain.entities.BarberDTO
import com.example.hairbookfront.domain.entities.BarberSignUpRequest
import com.example.hairbookfront.domain.entities.CustomerDTO
import com.example.hairbookfront.domain.entities.CustomerSignUpRequest
import com.example.hairbookfront.domain.entities.LoginRequest
import com.example.hairbookfront.domain.entities.User
import retrofit2.Response
import javax.inject.Inject


/**
 * Implementation of the [HairBookDataSourceAuth] interface.
 */
class HairBookDataSourceImplAuth @Inject constructor(
    private val apiServiceAuth: ApiServiceAuth
) : HairBookDataSourceAuth {

    override suspend fun login(email: String, password: String): Response<User> {
        val loginRequest = LoginRequest(email, password)
        return apiServiceAuth.login(loginRequest)
    }

    override suspend fun signOut(accessToken: String): Response<String> {
        return apiServiceAuth.signOut("Bearer $accessToken")
    }

    override suspend fun signUpCustomer(customerSignUpRequest: CustomerSignUpRequest): Response<User> {
        return apiServiceAuth.signUpCustomer(customerSignUpRequest)
    }

    override suspend fun signUpBarber(barberSignUpRequest: BarberSignUpRequest): Response<User> {
        return apiServiceAuth.signUpBarber(barberSignUpRequest)
    }

    override suspend fun getDetailsCustomer(accessToken: String): Response<CustomerDTO> {
        return apiServiceAuth.getDetailsCustomer("Bearer $accessToken")
    }

    override suspend fun getDetailsBarber(accessToken: String): Response<BarberDTO> {
        return apiServiceAuth.getDetailsBarber("Bearer $accessToken")
    }


}