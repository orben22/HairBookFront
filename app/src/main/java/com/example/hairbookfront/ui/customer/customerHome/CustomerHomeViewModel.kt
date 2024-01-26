package com.example.hairbookfront.ui.customer.customerHome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hairbookfront.data.datastore.DataStorePreferences
import com.example.hairbookfront.domain.SignOutHandler
import com.example.hairbookfront.domain.entities.BarberShop
import com.example.hairbookfront.domain.repository.ApiRepositoryCustomer
import com.example.hairbookfront.ui.navgraph.Routes
import com.example.hairbookfront.util.ResourceState
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
    private val signOutHandler: SignOutHandler,
    private val hairBookRepository: ApiRepositoryCustomer,
    private val dataStorePreferences: DataStorePreferences,
) : ViewModel() {

    private val _accessToken = MutableStateFlow("")
    val accessToken: StateFlow<String>
        get() = _accessToken

    private val _isExpanded = MutableStateFlow(false)
    val isExpanded: StateFlow<Boolean>
        get() = _isExpanded

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
    private val _screen = MutableStateFlow("")
    val screen: StateFlow<String>
        get() = _screen

    fun onSearchTextChanged(text: String) {
        _searchText.value = text
    }

    fun onBarberShopClicked(barberShop: BarberShop) {
        Timber.d("Clicked barbershop with ID: ${barberShop.barberShopId}")
        _screen.value = Routes.ViewShopScreen.route
        viewModelScope.launch {
            barberShop.barberShopId?.let { dataStorePreferences.setShopId(it) }
        }

    }

    fun expandedFun() {
        _isExpanded.value = !_isExpanded.value
    }

    fun dismissMenu(){
        _isExpanded.value = false
    }

    fun signOut() {
        viewModelScope.launch {
            signOutHandler.signOut(_accessToken.value)
            _screen.emit(Routes.WelcomeScreen.route)
        }
    }

    init {
        viewModelScope.launch {
            dataStorePreferences.getAccessToken().collectLatest { accessToken ->
                _accessToken.emit(accessToken)
                hairBookRepository.getAllShops(accessToken).collectLatest { response ->
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
