package com.example.hairbookfront.data.remote.DataSourcesImpls

import com.example.hairbookfront.data.remote.ApiServices.ApiServiceReview
import com.example.hairbookfront.data.remote.DataSources.HairBookDataSourceReview
import com.example.hairbookfront.domain.entities.Review
import retrofit2.Response
import javax.inject.Inject

class HairBookDataSourceImplReview @Inject constructor(
    private val apiServiceReview: ApiServiceReview
) : HairBookDataSourceReview {
    override suspend fun postReview(accessToken: String, review: Review): Response<Review> {
        return apiServiceReview.postReview(accessToken, review)
    }

    override suspend fun deleteReview(accessToken: String, reviewId: String): Response<String> {
        return apiServiceReview.deleteReview(accessToken, reviewId)
    }
}