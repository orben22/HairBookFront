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
            dataStorePreferences.getAccessToken().collectLatest { it ->
                hairBookRepository.getAllShops("Bearer $it").collectLatest {
                    when (it) {
                        is ResourceState.SUCCESS -> {
                            Timber.d("success: ${it.data.data}")
                        }

                        is ResourceState.ERROR -> {
                            Timber.d("error: ${it.error}")
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