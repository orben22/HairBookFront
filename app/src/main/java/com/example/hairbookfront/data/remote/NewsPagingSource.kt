package com.example.hairbookfront.data.remote

import com.example.hairbookfront.domain.entities.HairBookResponse
import com.example.hairbookfront.domain.entities.LoginRequest
import retrofit2.Response
import javax.inject.Inject

class HairBookDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : HairBookDataSource {

    override suspend fun login(email: String, password: String): Response<HairBookResponse> {
        val loginRequest = LoginRequest(email, password)
        return apiService.login(loginRequest)
    }

    override suspend fun signUp(str: String): Response<HairBookResponse> {
        return apiService.signUp(str)
    }


}