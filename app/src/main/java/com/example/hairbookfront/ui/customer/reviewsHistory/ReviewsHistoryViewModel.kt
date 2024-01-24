package com.example.hairbookfront.ui.customer.reviewsHistory

import androidx.lifecycle.ViewModel
import com.example.hairbookfront.data.datastore.DataStorePreferences
import com.example.hairbookfront.domain.repository.ApiRepositoryReview
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReviewsHistoryViewModel @Inject constructor(
    private val hairBookRepositoryReview: ApiRepositoryReview,
    private val dataStorePreferences: DataStorePreferences,
) : ViewModel() {

}