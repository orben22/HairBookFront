package com.example.hairbookfront.ui.customer.customerDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hairbookfront.data.datastore.DataStorePreferences
import com.example.hairbookfront.domain.SignOutHandler
import com.example.hairbookfront.domain.entities.Booking
import com.example.hairbookfront.domain.entities.Service
import com.example.hairbookfront.domain.repository.ApiRepositoryBooking
import com.example.hairbookfront.domain.repository.ApiRepositoryCustomer
import com.example.hairbookfront.ui.navgraph.Routes
import com.example.hairbookfront.util.Constants
import com.example.hairbookfront.util.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CustomerDetailsViewModel @Inject constructor(
    private val signOutHandler: SignOutHandler,
    private val apiRepositoryCustomer: ApiRepositoryCustomer,
    private val apiRepositoryBooking: ApiRepositoryBooking,
    private val dataStorePreferences: DataStorePreferences
) : ViewModel() {

    private val _accessToken = MutableStateFlow("")
    val accessToken: StateFlow<String>
        get() = _accessToken

    private val _isExpanded = MutableStateFlow(false)
    val isExpanded: StateFlow<Boolean>
        get() = _isExpanded

    private val _screen = MutableStateFlow("")
    val screen: StateFlow<String>
        get() = _screen

    private val _firstName = MutableStateFlow("")
    val firstName: StateFlow<String>
        get() = _firstName
    private val _lastName = MutableStateFlow("")
    val lastName: StateFlow<String>
        get() = _lastName

    private val _email = MutableStateFlow("")
    val email: StateFlow<String>
        get() = _email

    private val _age = MutableStateFlow(0)
    val age: StateFlow<Int>
        get() = _age
    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber: StateFlow<String>
        get() = _phoneNumber

    private val _closestBooking =
        MutableStateFlow<Booking?>(null)
    val closestBooking: StateFlow<Booking?>
        get() = _closestBooking

    private val _serviceDetails = MutableStateFlow<Service?>(null)
    val serviceDetails: StateFlow<Service?>
        get() = _serviceDetails
    private val showOrHideDeleteDialog = MutableStateFlow(false)
    val showOrHideDeleteDialogState: StateFlow<Boolean>
        get() = showOrHideDeleteDialog

    fun showOrHideDeleteDialog() {
        showOrHideDeleteDialog.value = !showOrHideDeleteDialog.value
    }

    fun getFirstName(): Flow<String> {
        return dataStorePreferences.getFirstName()
    }

    fun getLastName(): Flow<String> {
        return dataStorePreferences.getLastName()
    }

    fun getEmail(): Flow<String> {
        return dataStorePreferences.getEmail()
    }

    fun getAge(): Flow<Float> {
        return dataStorePreferences.getAge()
    }

    fun getPhoneNumber(): Flow<String> {
        return dataStorePreferences.getPhoneNumber()
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

    fun deleteBooking() {
        viewModelScope.launch {
            closestBooking.value?.bookingId?.let {
                apiRepositoryBooking.deleteBooking(_accessToken.value, it)
                    .collectLatest { response ->
                        Timber.d("res: $response")
                        when (response) {
                            is ResourceState.LOADING -> {
                            }

                            is ResourceState.SUCCESS -> {
                                showOrHideDeleteDialog.emit(false)
                            }

                            is ResourceState.ERROR -> {
                            }
                        }
                    }
            }
        }
    }

    init {
        getClosestBooking()
    }

    private fun getClosestBooking() {
        viewModelScope.launch {
            _accessToken.value = dataStorePreferences.getAccessToken().first()
            apiRepositoryBooking.getClosestBooking(_accessToken.value).collect { response ->
                Timber.d("res: $response")
                when (response) {
                    is ResourceState.LOADING -> {
                    }

                    is ResourceState.SUCCESS -> {
                        _closestBooking.emit(response.data)
                        getServiceDetails(response.data.serviceId)
                    }

                    is ResourceState.ERROR -> {
                    }
                }
            }
        }
    }

    fun editBookingClicked(booking: Booking) {
        viewModelScope.launch {
            dataStorePreferences.setMode(Constants.EditMode)
            booking.bookingId?.let { dataStorePreferences.setBookingIdForEditing(it) }
            dataStorePreferences.setShopId(booking.barberShopId)
            _screen.emit(Routes.EditOrCreateBookingScreen.route)
        }
    }

    private fun getServiceDetails(serviceId: String?) {
        viewModelScope.launch {
            serviceId?.let {
                apiRepositoryBooking.getServiceBookings(_accessToken.value, it)
                    .collect { response ->
                        when (response) {
                            is ResourceState.LOADING -> {
                            }

                            is ResourceState.SUCCESS -> {
                                _serviceDetails.emit(response.data)
                            }

                            is ResourceState.ERROR -> {
                            }
                        }
                    }
            }
        }
    }
}

