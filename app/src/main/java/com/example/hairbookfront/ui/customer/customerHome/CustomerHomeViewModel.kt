package com.example.hairbookfront.ui.customer.customerHome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hairbookfront.di.DataStorePreferences
import com.example.hairbookfront.domain.entities.BarberShop
import com.example.hairbookfront.domain.repository.ApiRepository
import com.example.hairbookfront.util.ResourceState
import com.google.gson.Gson
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.reflect.TypeToken

@HiltViewModel
class CustomerHomeViewModel @Inject constructor(
    private val hairBookRepository: ApiRepository,
    private val dataStorePreferences: DataStorePreferences,
    private val moshi: Moshi
) : ViewModel() {

    private val _shopsFromServer = MutableSharedFlow<List<BarberShop>>()
    val shopsFromServer: MutableSharedFlow<List<BarberShop>>
        get() = _shopsFromServer
    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String>
        get() = _searchText

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean>
        get() = _isSearching

    private val _barberShops = MutableStateFlow(listOf<BarberShop>())
    val barberShops = searchText.combine(_barberShops) { text, barberShops ->
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
                    when (response) {
                        is ResourceState.SUCCESS -> {
                            println("Raw JSON Data ${response.data.data}")
                            val gson = Gson()
                            val barberShopsType = object : TypeToken<List<BarberShop>>() {}.type

                            val barberShops: List<BarberShop> =
                                gson.fromJson(response.data.data.toString(), barberShopsType)


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