package com.example.hairbookfront.data.datasource

import com.example.hairbookfront.data.entity.NewsResponse
import retrofit2.Response

interface NewsDataSource {

    suspend fun getNewsHeadline(country: String): Response<NewsResponse>
}