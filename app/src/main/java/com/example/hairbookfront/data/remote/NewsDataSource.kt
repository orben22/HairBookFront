package com.example.hairbookfront.data.remote

import com.example.hairbookfront.domain.entities.HairBookResponse
import retrofit2.Response

interface HairBookDataSource {
    suspend fun login(email: String, password: String): Response<HairBookResponse>

    suspend fun signUp(str: String): Response<HairBookResponse>
}