package com.example.hairbookfront.data.remote.ApiServices

import com.example.hairbookfront.domain.entities.Review
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiServiceReview {
    @POST("review/post-review")
    suspend fun postReview(
        @Header("Authorization") authToken: String,
        @Body review: Review
    ): Response<Review>

    @DELETE("review/delete-review")
    suspend fun deleteReview(
        @Header("Authorization") authToken: String,
        @Query("reviewId") reviewId: String,
    ): Response<String>

    @PUT("review/update-review")
    suspend fun updateReview(
        @Header("Authorization") authToken: String,
        @Query("reviewId") reviewId: String,
        @Body review: Review
    ): Response<Review>

    @GET("review/get-my-reviews")
    suspend fun getMyReviews(
        @Header("Authorization") authToken: String
    ): Response<List<Review>>

    @GET("review/get-reviews")
    suspend fun getReviews(
        @Header("Authorization") authToken: String,
        @Query("barberShopId") barberShopId: String
    ): Response<List<Review>>

    @GET("review/get-review-by-id")
    suspend fun getReviewById(
        @Header("Authorization") authToken: String,
        @Query("reviewId") reviewId: String
    ): Response<Review>


}