package com.example.hairbookfront.data.remote.dataSources

import com.example.hairbookfront.domain.entities.BarberDTO
import com.example.hairbookfront.domain.entities.BarberSignUpRequest
import com.example.hairbookfront.domain.entities.CustomerDTO
import com.example.hairbookfront.domain.entities.CustomerSignUpRequest
import com.example.hairbookfront.domain.entities.User
import retrofit2.Response

interface HairBookDataSourceAuth {
    suspend fun login(email: String, password: String): Response<User>
    suspend fun signOut(accessToken: String): Response<String>

    suspend fun signUpBarber(barberSignUpRequest: BarberSignUpRequest): Response<User>

    suspend fun signUpCustomer(customerSignUpRequest: CustomerSignUpRequest): Response<User>

    suspend fun getDetailsCustomer(accessToken: String): Response<CustomerDTO>

    suspend fun getDetailsBarber(accessToken: String): Response<BarberDTO>

}