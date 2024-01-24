package com.example.hairbookfront.ui.shared.editOrCreateBooking

import androidx.lifecycle.ViewModel
import com.example.hairbookfront.data.datastore.DataStorePreferences
import com.example.hairbookfront.domain.repository.ApiRepositoryBarber
import com.example.hairbookfront.domain.repository.ApiRepositoryBooking
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditOrCreateBookingViewModel @Inject constructor(
    private val hairBookRepositoryBooking: ApiRepositoryBooking,
    private val hairBookRepositoryBarber: ApiRepositoryBarber,
    private val dataStorePreferences: DataStorePreferences,
) : ViewModel() {

}