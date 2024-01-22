package com.example.hairbookfront.ui.customer.customerHome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hairbookfront.di.DataStorePreferences
import com.example.hairbookfront.domain.repository.ApiRepository
import com.example.hairbookfront.util.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CustomerHomeViewModel @Inject constructor(
    private val hairBookRepository: ApiRepository,
    private val dataStorePreferences: DataStorePreferences
) : ViewModel() {

    init {
        viewModelScope.launch {
            dataStorePreferences.getAccessToken().collectLatest { accessToken ->
                hairBookRepository.getAllShops("Bearer $accessToken").collectLatest {response->
                    when (response) {
                        is ResourceState.SUCCESS -> {
                            Timber.d("success: ${response.data.data}")
                        }

                        is ResourceState.ERROR -> {
                            Timber.d("error: ${response.error}")
                        }

                        is ResourceState.LOADING -> {
                            Timber.d("loading")
                        }
                    }
                }
            }
        }
    }
}