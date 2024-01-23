package com.example.hairbookfront.data.remote.DataSourcesImpls

import com.example.hairbookfront.data.remote.ApiServices.ApiServiceAuth
import com.example.hairbookfront.data.remote.ApiServices.ApiServiceBarber
import com.example.hairbookfront.data.remote.ApiServices.ApiServiceBooking
import com.example.hairbookfront.data.remote.ApiServices.ApiServiceReview
import com.example.hairbookfront.data.remote.DataSources.HairBookDataSourceAuth
import com.example.hairbookfront.domain.entities.BarberDTO
import com.example.hairbookfront.domain.entities.BarberShop
import com.example.hairbookfront.domain.entities.BarberSignUpRequest
import com.example.hairbookfront.domain.entities.Booking
import com.example.hairbookfront.domain.entities.CustomerDTO
import com.example.hairbookfront.domain.entities.CustomerSignUpRequest
import com.example.hairbookfront.domain.entities.LoginRequest
import com.example.hairbookfront.domain.entities.Review
import com.example.hairbookfront.domain.entities.Service
import com.example.hairbookfront.domain.entities.User
import retrofit2.Response
import javax.inject.Inject

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