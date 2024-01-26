package com.example.hairbookfront.ui.shared.MyBookings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hairbookfront.data.datastore.DataStorePreferences
import com.example.hairbookfront.domain.SignOutHandler
import com.example.hairbookfront.domain.entities.Booking
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

    private val _bookings = MutableStateFlow(listOf<Booking>())
    val bookings: StateFlow<List<Booking>>
        get() = _bookings

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

    fun deleteBookings(booking: Booking) {
        viewModelScope.launch {
            Timber.d(booking.toString())
            if (role.value == Constants.BarberRole) {
                booking.bookingId?.let {
                    hairBookRepositoryBarber.deleteBooking(accessToken.value,booking.barberShopId,
                        it
                    )
                }
            } else {
                booking.bookingId?.let {
                    hairBookRepositoryBooking.deleteBooking(accessToken.value,
                        it
                    )
                }
            }
        }
    }

    fun editBookings(booking: Booking){
        viewModelScope.launch {
            dataStorePreferences.setMode(Constants.EditMode)
            booking.bookingId?.let { dataStorePreferences.setBookingIdForEditing(it) }
            _screen.emit(Routes.EditOrCreateBookingScreen.route)
        }
    }

    init {
        viewModelScope.launch {
            _accessToken.emit(dataStorePreferences.getAccessToken().first())
            dataStorePreferences.getRole().collectLatest { role ->
                Timber.d(role)
                _role.emit(role)
                hairBookRepositoryBooking.getUserBookings(accessToken.value).collectLatest {response ->
                    when (response) {
                        is ResourceState.LOADING -> {
                            Timber.d("Loading")
                        }

                        is ResourceState.SUCCESS -> {
                            Timber.d("Success")
                            _bookings.emit(response.data)
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