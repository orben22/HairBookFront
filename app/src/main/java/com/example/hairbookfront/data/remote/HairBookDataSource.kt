package com.example.hairbookfront.data.remote

import com.example.hairbookfront.domain.entities.BarberShop
import com.example.hairbookfront.domain.entities.User
import com.example.hairbookfront.domain.entities.UserSignUpRequest
import retrofit2.Response

interface HairBookDataSource {
    suspend fun login(email: String, password: String): Response<User>

    suspend fun getAllShops(accessToken: String): Response<List<BarberShop>>

    suspend fun signUp(signUpRequest: UserSignUpRequest): Response<User>
}