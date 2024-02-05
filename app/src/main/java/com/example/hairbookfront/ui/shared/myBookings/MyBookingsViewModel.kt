package com.example.hairbookfront.ui.shared.myBookings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hairbookfront.data.datastore.DataStorePreferences
import com.example.hairbookfront.domain.SignOutHandler
import com.example.hairbookfront.domain.entities.Booking
import com.example.hairbookfront.domain.entities.Service
import com.example.hairbookfront.domain.repository.ApiRepositoryBarber
import com.example.hairbookfront.domain.repository.ApiRepositoryBooking
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


/**
 * ViewModel for the MyBookings screen.
 *
 * @property signOutHandler The handler for signing out.
 * @property hairBookRepositoryBooking The repository for booking related operations.
 * @property hairBookRepositoryBarber The repository for barber related operations.
 * @property dataStorePreferences The datastore for storing preferences.
 */
@HiltViewModel
class MyBookingsViewModel @Inject constructor(
    private val signOutHandler: SignOutHandler,
    private val hairBookRepositoryBooking: ApiRepositoryBooking,
    private val hairBookRepositoryBarber: ApiRepositoryBarber,
    private val dataStorePreferences: DataStorePreferences,
) : ViewModel() {

    private val _accessToken = MutableStateFlow("")
    val accessToken: StateFlow<String>
        get() = _accessToken


    private val _role = MutableStateFlow("")
    val role: StateFlow<String>
        get() = _role

    private val _isExpanded = MutableStateFlow(false)
    val isExpanded: StateFlow<Boolean>
        get() = _isExpanded

    private val _screen = MutableStateFlow("")
    val screen: StateFlow<String>
        get() = _screen

    private val _lastScreen = MutableStateFlow(false)
    val lastScreen: StateFlow<Boolean>
        get() = _lastScreen

    private val _bookings = MutableStateFlow(listOf<Booking>())
    val bookings: StateFlow<List<Booking>>
        get() = _bookings
    private val _services = MutableStateFlow(listOf<Service>())
    val services: StateFlow<List<Service>>
        get() = _services

    fun clearScreen(){
        _screen.value = ""
    }
    fun onBackClicked() {
        _lastScreen.value = true
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

    suspend fun deleteBookingsBarber(booking: Booking) {
        booking.bookingId?.let {
            hairBookRepositoryBarber.deleteBooking(
                accessToken.value, booking.barberShopId, it).collectLatest {response->
                when (response) {
                    is ResourceState.LOADING -> {
                        Timber.d("Loading")
                    }

                    is ResourceState.SUCCESS -> {
                        Timber.d("Success")
                        _bookings.emit(_bookings.value.filter { booking1 ->
                            booking1.bookingId != booking.bookingId
                        })
                    }
                    is ResourceState.ERROR -> {
                        Timber.d("Error")
                    }
                }
            }
        }
    }

    suspend fun deleteBookingsCustomer(booking: Booking) {
        booking.bookingId?.let { bookingId ->
            hairBookRepositoryBooking.deleteBooking(
                accessToken.value,
                bookingId
            ).collectLatest {response->
                when (response) {
                    is ResourceState.LOADING -> {
                        Timber.d("Loading")
                    }

                    is ResourceState.SUCCESS -> {
                        Timber.d("Success")
                        _bookings.emit(_bookings.value.filter { booking1 ->
                            booking1.bookingId != booking.bookingId
                        })
                    }
                    is ResourceState.ERROR -> {
                        Timber.d("Error")
                    }
                }
            }
        }
    }
    fun deleteBookings(booking: Booking) {
        viewModelScope.launch {
            if (role.value == Constants.BarberRole) {
                deleteBookingsBarber(booking)
            } else {
                deleteBookingsCustomer(booking)
            }
        }
    }


    fun editBookings(booking: Booking) {
        viewModelScope.launch {
            dataStorePreferences.setMode(Constants.EditMode)
            dataStorePreferences.setShopId(booking.barberShopId)
            booking.bookingId?.let { dataStorePreferences.setBookingIdForEditing(it) }
            _screen.emit(Routes.EditOrCreateBookingScreen.route)
        }
    }

    fun profileClicked() {
        if (_role.value == Constants.BarberRole)
            _screen.value = Routes.BarberDetailsScreen.route
        else
            _screen.value = Routes.CustomerDetailsScreen.route
    }

    private fun getServiceDetails(serviceId: String?) {
        viewModelScope.launch {
            serviceId?.let {
                hairBookRepositoryBooking.getServiceBookings(_accessToken.value, it)
                    .collect { response ->
                        Timber.d("res: $response")
                        when (response) {
                            is ResourceState.LOADING -> {
                            }

                            is ResourceState.SUCCESS -> {
                                response.data.let { service ->
                                    val currentServices = _services.value.toMutableList()
                                    currentServices.add(service)
                                    _services.emit(currentServices)
                                }
                            }

                            is ResourceState.ERROR -> {
                            }
                        }
                    }
            }
        }
    }
    private fun getCustomerBookings() {
        viewModelScope.launch {
            hairBookRepositoryBooking.getUserBookings(_accessToken.value)
                .collectLatest { response ->
                    Timber.d("res: $response")
                    when (response) {
                        is ResourceState.LOADING -> {
                        }

                        is ResourceState.SUCCESS -> {
                            _bookings.emit(response.data)
                            response.data.forEach { booking ->
                                getServiceDetails(booking.serviceId)
                            }
                        }

                        is ResourceState.ERROR -> {
                        }
                    }
                }
        }
    }

    private fun getBarberBookings() {
        viewModelScope.launch {
            hairBookRepositoryBarber.getBarberBookings(_accessToken.value)
                .collectLatest { response ->
                    Timber.d("res: $response")
                    when (response) {
                        is ResourceState.LOADING -> {
                        }

                        is ResourceState.SUCCESS -> {
                            _bookings.emit(response.data)
                            response.data.forEach { booking ->
                                getServiceDetails(booking.serviceId)
                            }
                        }

                        is ResourceState.ERROR -> {
                        }
                    }
                }
        }
    }

    init {
        viewModelScope.launch {
            _accessToken.emit(dataStorePreferences.getAccessToken().first())
            dataStorePreferences.getRole().collectLatest {role ->
                _role.emit(role)
                if (role == Constants.BarberRole) {
                    getBarberBookings()
                } else {
                    getCustomerBookings()
                }
            }
        }
    }
}