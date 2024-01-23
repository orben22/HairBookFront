package com.example.hairbookfront.ui.customer.customerHome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hairbookfront.data.datastore.DataStorePreferences
import com.example.hairbookfront.domain.entities.BarberShop
import com.example.hairbookfront.domain.repository.ApiRepositoryAuth
import com.example.hairbookfront.domain.repository.ApiRepositoryCustomer
import com.example.hairbookfront.util.ResourceState
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce

@OptIn(FlowPreview::class)
@HiltViewModel
class CustomerHomeViewModel @Inject constructor(
    private val hairBookRepository: ApiRepositoryCustomer,
    private val dataStorePreferences: DataStorePreferences,
    private val moshi: Moshi
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String>
        get() = _searchText

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean>
        get() = _isSearching

    private val _barberShops = MutableStateFlow(listOf<BarberShop>())
    val barberShops = searchText.debounce(1000L).combine(_barberShops) { text, barberShops ->
        if (text.isBlank()) {
            barberShops
        } else {
            barberShops.filter { it.doesMatchSearchQuery(text) }
        }
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(500),
        initialValue = _barberShops.value
    )

    fun onSearchTextChanged(text: String) {
        _searchText.value = text
    }


    init {
        viewModelScope.launch {
            dataStorePreferences.getAccessToken().collectLatest { accessToken ->
                hairBookRepository.getAllShops("Bearer $accessToken").collectLatest { response ->
                    Timber.d("response: $response")
                    when (response) {
                        is ResourceState.LOADING -> {
                            Timber.d("Loading")
                        }

                        is ResourceState.SUCCESS -> {
                            Timber.d("Success")
                            _barberShops.emit(response.data)
                        }

                        is ResourceState.ERROR -> {
                            Timber.d("Error")
                        }
                    }
                }
            }
        }
    }
}
