package com.example.hairbookfront.data.remote.apiServices

import com.example.hairbookfront.domain.entities.Review
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

/**
 * Interface for review related API services.
 */
interface ApiServiceReview {

    /**
     * Posts a review.
     *
     * @param authToken The authorization token of the user.
     * @param review The review to be posted.
     * @return A response containing the posted review.
     */
    @POST("review/post-review")
    suspend fun postReview(
        @Header("Authorization") authToken: String,
        @Body review: Review
    ): Response<Review>

    /**
     * Deletes a review.
     *
     * @param authToken The authorization token of the user.
     * @param reviewId The id of the review.
     * @return A response containing the id of the deleted review.
     */
    @DELETE("review/delete-review")
    suspend fun deleteReview(
        @Header("Authorization") authToken: String,
        @Query("reviewId") reviewId: String,
    ): Response<String>

    /**
     * Updates a review.
     *
     * @param authToken The authorization token of the user.
     * @param reviewId The id of the review.
     * @param review The updated review details.
     * @return A response containing the updated review details.
     */
    @PUT("review/update-review")
    suspend fun updateReview(
        @Header("Authorization") authToken: String,
        @Query("reviewId") reviewId: String,
        @Body review: Review
    ): Response<Review>

    /**
     * Gets all the reviews posted by the authenticated user.
     *
     * @param authToken The authorization token of the user.
     * @return A response containing a list of reviews.
     */
    @GET("review/get-my-reviews")
    suspend fun getMyReviews(
        @Header("Authorization") authToken: String
    ): Response<List<Review>>

    /**
     * Gets all the reviews of a barber shop.
     *
     * @param authToken The authorization token of the user.
     * @param barberShopId The id of the barber shop.
     * @return A response containing a list of reviews.
     */
    @GET("shared/get-reviews")
    suspend fun getReviews(
        @Header("Authorization") authToken: String,
        @Query("barberShopId") barberShopId: String
    ): Response<List<Review>>

    /**
     * Gets a review by its id.
     *
     * @param authToken The authorization token of the user.
     * @param reviewId The id of the review.
     * @return A response containing the review.
     */
    @GET("review/get-review-by-id")
    suspend fun getReviewById(
        @Header("Authorization") authToken: String,
        @Query("reviewId") reviewId: String
    ): Response<Review>


}