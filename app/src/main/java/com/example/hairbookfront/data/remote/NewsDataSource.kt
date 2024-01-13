package com.example.hairbookfront.data.remote

import com.example.hairbookfront.domain.model.NewsResponse
import retrofit2.Response

interface NewsDataSource {

    suspend fun getNewsHeadline(country: String): Response<NewsResponse>
}