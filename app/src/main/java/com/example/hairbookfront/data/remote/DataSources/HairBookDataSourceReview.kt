package com.example.hairbookfront.data.remote.DataSources

import com.example.hairbookfront.domain.entities.Review
import retrofit2.Response

interface HairBookDataSourceReview {
    suspend fun postReview(accessToken: String, review: Review): Response<Review>

    suspend fun deleteReview(accessToken: String, reviewId: String): Response<String>

    suspend fun updateReview(accessToken: String, reviewId: String, review: Review): Response<Review>

    suspend fun getMyReviews(accessToken: String): Response<List<Review>>

    suspend fun getReviews(accessToken: String, barbershopId: String): Response<List<Review>>
}