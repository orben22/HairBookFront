package com.example.hairbookfront.ui.shared.viewShop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hairbookfront.data.datastore.DataStorePreferences
import com.example.hairbookfront.domain.entities.BarberShop
import com.example.hairbookfront.domain.repository.ApiRepositoryCustomer
import com.example.hairbookfront.util.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ViewShopViewModel @Inject constructor(
    private val hairBookRepository: ApiRepositoryCustomer,
    private val dataStorePreferences: DataStorePreferences,
) : ViewModel() {

    private val _accessToken = MutableStateFlow("")
    private val _dataLoaded = MutableStateFlow(false)
    val dataLoaded: StateFlow<Boolean>
        get() = _dataLoaded

    private
    val accessToken: StateFlow<String>
        get() = _accessToken

    private val _barberShop = MutableStateFlow(
        BarberShop(
            "",
            "",
            "",
            "",
            listOf(),
            listOf(),
            listOf(),
            listOf(),
            listOf(),
            listOf(),
            listOf(),
            listOf(),
            5f,
            "",
            "",
        )
    )
    val barberShop: StateFlow<BarberShop>
        get() = _barberShop
    private val _shopId = MutableStateFlow("")
    val shopId: StateFlow<String>
        get() = _shopId

    private val _role = MutableStateFlow("")
    val role: StateFlow<String>
        get() = _role


    fun dataLoaded() {
        _dataLoaded.value = true
    }

    fun getShopData() {
        viewModelScope.launch {
            _shopId.emit(dataStorePreferences.getShopId().first())
            _role.emit(dataStorePreferences.getRole().first())
            _accessToken.emit(dataStorePreferences.getAccessToken().first())
            hairBookRepository.getShopById(_accessToken.value, _shopId.value)
                .collect { resourceState ->
                    when (resourceState) {
                        is ResourceState.SUCCESS -> {
                            _barberShop.emit(resourceState.data)
                        }

                        is ResourceState.ERROR -> {
                            Timber.d(resourceState.error)
                        }

                        is ResourceState.LOADING -> {
                            Timber.d("Loading")
                        }
                    }
                }
        }
    }
}
