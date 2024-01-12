package com.example.hairbookfront.ui.repository

import com.example.hairbookfront.data.datasource.NewsDataSource
import com.example.hairbookfront.data.entity.NewsResponse
import com.example.utilities.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HairBookRepository @Inject constructor(private val newsDataSource: NewsDataSource) {

//    suspend fun getNewsHeadline(country: String): Response<NewsResponse> {
//        return newsDataSource.getNewsHeadline(country)
//    }

    suspend fun getNewsHeadline(country: String): Flow<ResourceState<NewsResponse>> {
        return flow {
            emit(ResourceState.LOADING())
            val response = newsDataSource.getNewsHeadline(country)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.SUCCESS(response.body()!!))
            } else {
                emit(ResourceState.ERROR("Error fetching news data"))
            }
        }.catch { e ->
            emit(ResourceState.ERROR(e.localizedMessage ?: "Something went wrong with api"))
        }
    }
}