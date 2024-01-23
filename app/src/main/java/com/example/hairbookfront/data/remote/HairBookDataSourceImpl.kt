package com.example.hairbookfront.data.remote

import com.example.hairbookfront.domain.entities.BarberShop
import com.example.hairbookfront.domain.entities.LoginRequest
import com.example.hairbookfront.domain.entities.User
import retrofit2.Response
import javax.inject.Inject

class HairBookDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : HairBookDataSource {

    override suspend fun login(email: String, password: String): Response<User> {
        val loginRequest = LoginRequest(email, password)
        return apiService.login(loginRequest)
    }

    override suspend fun getAllShops(accessToken: String): Response<List<BarberShop>> {
        return apiService.getAllShops(accessToken)
    }


}