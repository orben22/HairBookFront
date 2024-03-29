package com.example.hairbookfront.domain.repository

import com.example.hairbookfront.data.remote.dataSources.HairBookDataSourceReview
import com.example.hairbookfront.domain.entities.Review
import com.example.hairbookfront.util.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApiRepositoryReview @Inject constructor(
    private val hairBookDataSourceReview: HairBookDataSourceReview
) {

    /**
     * Posts a review.
     *
     * @param accessToken The access token of the user.
     * @param review The review to be posted.
     * @return A flow of [ResourceState] of [Review].
     */
    suspend fun postReview(
        accessToken: String,
        review: Review
    ): Flow<ResourceState<Review>> {
        return flow {
            emit(ResourceState.LOADING())
            val response = hairBookDataSourceReview.postReview(accessToken, review)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.SUCCESS(response.body()!!))
            } else {
                emit(ResourceState.ERROR("Error to post review"))
            }
        }.catch { e ->
            emit(ResourceState.ERROR(e.localizedMessage ?: "Something went wrong with api"))
        }
    }

    /**
     * Deletes a review.
     *
     * @param accessToken The access token of the user.
     * @param reviewId The id of the review to be deleted.
     * @return A flow of [ResourceState] of [String].
     */
    suspend fun deleteReview(
        accessToken: String,
        reviewId: String
    ): Flow<ResourceState<String>> {
        return flow {
            emit(ResourceState.LOADING())
            val response = hairBookDataSourceReview.deleteReview(accessToken, reviewId)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.SUCCESS(response.body()!!))
            } else {
                emit(ResourceState.ERROR("Delete review failed"))
            }
        }.catch { e ->
            emit(ResourceState.ERROR(e.localizedMessage ?: "Something went wrong with api"))
        }
    }

    /**
     * Updates a review.
     *
     * @param accessToken The access token of the user.
     * @param reviewId The id of the review to be updated.
     * @param review The review to be updated.
     * @return A flow of [ResourceState] of [Review].
     */
    suspend fun updateReview(
        accessToken: String,
        reviewId: String,
        review: Review
    ): Flow<ResourceState<Review>> {
        return flow {
            emit(ResourceState.LOADING())
            val response = hairBookDataSourceReview.updateReview(accessToken, reviewId, review)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.SUCCESS(response.body()!!))
            } else {
                emit(ResourceState.ERROR("Update review failed"))
            }
        }.catch { e ->
            emit(ResourceState.ERROR(e.localizedMessage ?: "Something went wrong with api"))
        }
    }

    /**
     * Gets all the reviews of the user.
     *
     * @param accessToken The access token of the user.
     * @return A flow of [ResourceState] of [List] of [Review].
     */
    suspend fun getMyReviews(
        accessToken: String
    ): Flow<ResourceState<List<Review>>> {
        return flow {
            emit(ResourceState.LOADING())
            val response = hairBookDataSourceReview.getMyReviews(accessToken)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.SUCCESS(response.body()!!))
            } else {
                emit(ResourceState.ERROR("Error getting my reviews"))
            }
        }.catch { e ->
            emit(ResourceState.ERROR(e.localizedMessage ?: "Something went wrong with api"))
        }
    }

    /**
     * Gets all the reviews of a barbershop.
     *
     * @param accessToken The access token of the user.
     * @param barbershopId The id of the barbershop.
     * @return A flow of [ResourceState] of [List] of [Review].
     */
    suspend fun getBarberShopReviews(
        accessToken: String,
        barbershopId: String
    ): Flow<ResourceState<List<Review>>> {
        return flow {
            emit(ResourceState.LOADING())
            val response = hairBookDataSourceReview.getReviews(accessToken, barbershopId)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.SUCCESS(response.body()!!))
            } else {
                emit(ResourceState.ERROR("Error getting reviews"))
            }
        }.catch { e ->
            emit(ResourceState.ERROR(e.localizedMessage ?: "Something went wrong with api"))
        }
    }

    /**
     * Gets a review by id.
     *
     * @param accessToken The access token of the user.
     * @param reviewId The id of the review.
     * @return A flow of [ResourceState] of [Review].
     */
    suspend fun getReviewById(
        accessToken: String,
        reviewId: String
    ): Flow<ResourceState<Review>> {
        return flow {
            emit(ResourceState.LOADING())
            val response = hairBookDataSourceReview.getReviewById(accessToken, reviewId)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.SUCCESS(response.body()!!))
            } else {
                emit(ResourceState.ERROR("Error getting review by id"))
            }
        }.catch { e ->
            emit(ResourceState.ERROR(e.localizedMessage ?: "Something went wrong with api"))
        }
    }
}