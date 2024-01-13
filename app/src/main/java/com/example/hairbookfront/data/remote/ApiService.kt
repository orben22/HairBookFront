package com.example.hairbookfront.data.remote

import com.example.hairbookfront.domain.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    suspend fun getNewsHeadline(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String = "624a77a2a4c24ba899f99bbc9e1b8aee"
    ): Response<NewsResponse>
}