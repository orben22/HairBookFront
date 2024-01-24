package com.example.hairbookfront.ui.shared.bookingHistory

import androidx.lifecycle.ViewModel
import com.example.hairbookfront.data.datastore.DataStorePreferences
import com.example.hairbookfront.domain.repository.ApiRepositoryBooking
import com.example.hairbookfront.domain.repository.ApiRepositoryReview
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class BookingHistoryViewModel @Inject constructor(
    private val hairBookRepositoryBooking: ApiRepositoryBooking,
    private val dataStorePreferences: DataStorePreferences,
) : ViewModel() {

}