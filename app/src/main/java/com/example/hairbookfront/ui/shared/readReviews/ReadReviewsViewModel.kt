package com.example.hairbookfront.ui.shared.readReviews

import androidx.lifecycle.ViewModel
import com.example.hairbookfront.data.datastore.DataStorePreferences
import com.example.hairbookfront.domain.repository.ApiRepositoryBarber
import com.example.hairbookfront.domain.repository.ApiRepositoryReview
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReadReviewsViewModel @Inject constructor(
    private val hairBookRepositoryReview: ApiRepositoryReview,
    private val hairBookRepositoryBarber: ApiRepositoryBarber,
    private val dataStorePreferences: DataStorePreferences,
) : ViewModel() {

}