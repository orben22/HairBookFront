package com.example.hairbookfront.ui.shared.viewShop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hairbookfront.data.datastore.DataStorePreferences
import com.example.hairbookfront.domain.SignOutHandler
import com.example.hairbookfront.domain.entities.BarberShop
import com.example.hairbookfront.domain.entities.Review
import com.example.hairbookfront.domain.entities.Service
import com.example.hairbookfront.domain.repository.ApiRepositoryBarber
import com.example.hairbookfront.domain.repository.ApiRepositoryCustomer
import com.example.hairbookfront.domain.repository.ApiRepositoryReview
import com.example.hairbookfront.ui.navgraph.Routes
import com.example.hairbookfront.util.Constants
import com.example.hairbookfront.util.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ViewShopViewModel @Inject constructor(
    private val signOutHandler: SignOutHandler,
    private val hairBookRepositoryCustomer: ApiRepositoryCustomer,
    private val hairBookRepositoryReview: ApiRepositoryReview,
    private val dataStorePreferences: DataStorePreferences,
) : ViewModel() {

    private val _accessToken = MutableStateFlow("")
    private val _dataLoaded = MutableStateFlow(false)

    private val _isExpanded = MutableStateFlow(false)
    val isExpanded: StateFlow<Boolean>
        get() = _isExpanded

    private val _screen = MutableStateFlow("")
    val screen: StateFlow<String>
        get() = _screen

    val dataLoaded: StateFlow<Boolean>
        get() = _dataLoaded

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

    private val _reviews = MutableStateFlow<List<Review>>(listOf())

    val reviews: StateFlow<List <Review>>
        get() = _reviews

    private val _shopId = MutableStateFlow("")

    private val _role = MutableStateFlow("")
    val role: StateFlow<String>
        get() = _role

    init {
        viewModelScope.launch {
            dataStorePreferences.getRole().collectLatest { role ->
                _role.emit(role)
            }
        }
        getShopData()
        getReviews()
    }


    fun onFloatingActionButtonClicked() {
        _screen.value = Routes.EditOrCreateBookingScreen.route
        viewModelScope.launch {
            dataStorePreferences.setMode(Constants.CreateMode)
            _barberShop.value.barberShopId?.let { dataStorePreferences.setShopId(it) }
        }
    }

    fun onEditShopClicked() {
        _screen.value = Routes.CreateBarberShopScreen.route
        viewModelScope.launch {
            dataStorePreferences.setMode(Constants.EditMode)
            _barberShop.value.barberShopId?.let { dataStorePreferences.setShopId(it) }
        }
    }

    private fun getReviews() {
        viewModelScope.launch {
            _shopId.emit(dataStorePreferences.getShopId().first())
            _accessToken.emit(dataStorePreferences.getAccessToken().first())
            hairBookRepositoryReview.getReviews(_accessToken.value, _shopId.value)
                .collectLatest { resourceState ->
                    when (resourceState) {
                        is ResourceState.SUCCESS -> {
                            _reviews.emit(resourceState.data)
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

    private fun getShopData() {
        viewModelScope.launch {
            _shopId.emit(dataStorePreferences.getShopId().first())
            _role.emit(dataStorePreferences.getRole().first())
            _accessToken.emit(dataStorePreferences.getAccessToken().first())
            hairBookRepositoryCustomer.getShopById(_accessToken.value, _shopId.value)
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

    fun expandedFun() {
        _isExpanded.value = !_isExpanded.value
    }

    fun dismissMenu() {
        _isExpanded.value = false
    }

    fun signOut() {
        viewModelScope.launch {
            signOutHandler.signOut(_accessToken.value)
            _screen.emit(Routes.WelcomeScreen.route)
        }
    }

    fun viewHistory() {
        viewModelScope.launch {
            _screen.emit(Routes.MyBookingsScreen.route)
        }
    }

    fun writeReview() {
        viewModelScope.launch {
            dataStorePreferences.setMode(Constants.CreateMode)
            dataStorePreferences.setShopId(_shopId.value)
            _screen.emit(Routes.EditOrCreateReviewScreen.route)
        }
    }
}
