package com.example.hairbookfront.ui.shared.editOrCreateBooking

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hairbookfront.data.datastore.DataStorePreferences
import com.example.hairbookfront.domain.SignOutHandler
import com.example.hairbookfront.domain.entities.BarberShop
import com.example.hairbookfront.domain.entities.Booking
import com.example.hairbookfront.domain.entities.Service
import com.example.hairbookfront.domain.repository.ApiRepositoryBooking
import com.example.hairbookfront.domain.repository.ApiRepositoryCustomer
import com.example.hairbookfront.ui.navgraph.Routes
import com.example.hairbookfront.util.Constants
import com.example.hairbookfront.util.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

/**
 * ViewModel for the EditOrCreateBooking screen.
 *
 * @property signOutHandler The handler for signing out.
 * @property hairBookRepositoryBooking The repository for booking related operations.
 * @property dataStorePreferences The datastore for storing preferences.
 * @property hairBookRepositoryCustomer The repository for customer related operations.
 */
@HiltViewModel
class EditOrCreateBookingViewModel @Inject constructor(
    private val signOutHandler: SignOutHandler,
    private val hairBookRepositoryBooking: ApiRepositoryBooking,
    private val dataStorePreferences: DataStorePreferences,
    private val hairBookRepositoryCustomer: ApiRepositoryCustomer,
) : ViewModel() {

    private val _mode = MutableStateFlow("")
    val mode: StateFlow<String>
        get() = _mode
    private val _bookingIdForEditing = MutableStateFlow("")
    private val _role = MutableStateFlow("")
    private val _booking = MutableStateFlow<Booking?>(null)

    private val _accessToken = MutableStateFlow("")
    val accessToken: StateFlow<String>
        get() = _accessToken

    private val _isExpanded = MutableStateFlow(false)
    val isExpanded: StateFlow<Boolean>
        get() = _isExpanded

    private val _screen = MutableStateFlow("")
    val screen: StateFlow<String>
        get() = _screen

    private val _lastScreen = MutableStateFlow(false)
    val lastScreen: StateFlow<Boolean>
        get() = _lastScreen

    private val _selectedService = MutableStateFlow<Service?>(null)
    val selectedService: StateFlow<Service?>
        get() = _selectedService

    private val _selectedDate = MutableStateFlow("")
    val selectedDate: StateFlow<String>
        get() = _selectedDate

    private val _availableBookingByDay = MutableStateFlow<List<Boolean>>(listOf())

    val availableBookingByDay: StateFlow<List<Boolean>>
        get() = _availableBookingByDay

    private val _showTimeSlots = MutableStateFlow(false)

    private val _selectedTimeSlot = MutableStateFlow("")
    val selectedTimeSlot: StateFlow<String>
        get() = _selectedTimeSlot

    private val _barberShopId = MutableStateFlow("")

    private val _shop = MutableStateFlow<BarberShop?>(null)
    val shop: StateFlow<BarberShop?>
        get() = _shop

    private val _services = MutableStateFlow<List<Service>>(listOf())
    val services: StateFlow<List<Service>>
        get() = _services

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    fun clearScreen(){
        _screen.value = ""
    }
    fun onBackClicked() {
        _lastScreen.value = true
    }
    fun onServiceSelected(service: Service) {
        _selectedService.value = service
    }

    fun onDateSelected(date: String) {
        _selectedDate.value = date
        _showTimeSlots.value = true
    }

    fun onTimeSlotSelected(timeSlot: String) {
        _selectedTimeSlot.value = timeSlot
    }

    init {
        viewModelScope.launch {
            _accessToken.emit(dataStorePreferences.getAccessToken().first())
            _barberShopId.emit(dataStorePreferences.getShopId().first())
            _role.emit(dataStorePreferences.getRole().first())
            _mode.emit(dataStorePreferences.getMode().first())
        }
        getShopById()
    }

    private suspend fun getBookingById() {
        hairBookRepositoryBooking.getBookingById(_accessToken.value, _bookingIdForEditing.value)
            .collectLatest { response ->
                when (response) {
                    is ResourceState.SUCCESS -> {
                        Timber.d("getBookingById: ${response.data}")
                        _booking.emit(response.data)
                        if (_booking.value != null) {
                            Timber.d("service ID ${_booking.value!!.serviceId}")
                            Timber.d("services ${_services.value}")
                            _selectedService.emit(_services.value.find { it.serviceId == _booking.value!!.serviceId })
                            val dateAndTime = _booking.value!!.date.split(" ")
                            _selectedDate.emit(dateAndTime[0])
                            _selectedTimeSlot.emit(dateAndTime[1])
                        }
                    }

                    is ResourceState.ERROR -> {
                        Timber.d(response.error)
                    }

                    is ResourceState.LOADING -> {
                        Timber.d("Loading")
                    }
                }
            }
    }

    private suspend fun getAllServicesByBarberShop() {
        hairBookRepositoryBooking.getAllServicesByBarberShop(
            _accessToken.value,
            _barberShopId.value
        )
            .collectLatest { response ->
                when (response) {
                    is ResourceState.SUCCESS -> {
                        _services.emit(response.data)
                        if (_mode.value == Constants.EditMode) {
                            _bookingIdForEditing.emit(
                                dataStorePreferences.getBookingIdForEditing().first()
                            )
                            getBookingById()
                        }
                    }

                    is ResourceState.ERROR -> {
                        Timber.d(response.error)
                    }

                    is ResourceState.LOADING -> {
                        Timber.d("Loading")
                    }
                }
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getAvailableBookingByDay() {
        viewModelScope.launch(Dispatchers.IO) {
            hairBookRepositoryBooking.getAvailableBookingByDay(
                _accessToken.value,
                _barberShopId.value,
                _selectedDate.value
            )
                .collectLatest { response ->
                    when (response) {
                        is ResourceState.SUCCESS -> {
                            Timber.d("getAvailableBookingByDay: ${response.data}")
                            val availableBookingByDay = response.data.toMutableList()
                            // Check if the app is in edit mode
                            if (_mode.value == Constants.EditMode) {
                                if (_booking.value != null) {
                                    val dateAndTime = _booking.value!!.date.split(" ")
                                    // Get the working hours for the selected date
                                    val workingHours = getWorkingHours()
                                    // Find the index of dateAndTime[1] in the working hours list
                                    val bookingTimeslotIndex = workingHours.indexOf(dateAndTime[1])
                                    // If the index is not -1, change the corresponding index in availableBookingByDay to true
                                    if (bookingTimeslotIndex != -1) {
                                        availableBookingByDay[bookingTimeslotIndex] = true
                                        _selectedTimeSlot.emit(dateAndTime[1])
                                    }
                                }
                            }
                            _availableBookingByDay.emit(availableBookingByDay)
                        }

                        is ResourceState.ERROR -> {
                            Timber.d(response.error)
                        }

                        is ResourceState.LOADING -> {
                            Timber.d("Loading")
                        }
                    }
                }
        }
    }

    fun bookHaircutIfPossible() {
        val service = _selectedService.value
        val date = _selectedDate.value
        val timeslot = _selectedTimeSlot.value
        if (service == null) {
            sendMessage("Please select a service")
            return
        }
        if (date.isEmpty()) {
            sendMessage("Please select a date")
            return
        }
        if (timeslot.isEmpty()) {
            sendMessage("Please select a time slot")
            return
        }
        val bookingDateTime = "$date $timeslot"
        viewModelScope.launch {
            val firstName = dataStorePreferences.getFirstName().first()
            val lastName = dataStorePreferences.getLastName().first()
            val userId = dataStorePreferences.getUserId().first()
            val booking = Booking(
                barberShopId = _barberShopId.value,
                serviceId = service.serviceId!!,
                date = bookingDateTime,
                barberShopName = _shop.value?.barberShopName ?: "",
                barberName = _shop.value?.barberName ?: "",
                customerName = "$firstName $lastName",
                userId = userId,
                bookingId = _bookingIdForEditing.value
            )
            if (_mode.value == Constants.CreateMode) {
                postBooking(booking)
            }
            else {
                Timber.d("updateBooking: ${booking.bookingId}")
                updateBooking(booking)
            }
        }
    }

    private suspend fun updateBooking(booking: Booking) {
        hairBookRepositoryBooking.updateBooking(
            _accessToken.value, _bookingIdForEditing.value, booking
        ).collectLatest { response ->
            when (response) {
                is ResourceState.SUCCESS -> {
                    Timber.d("updateBooking: ${response.data}")
                    _screen.emit(Routes.ViewShopScreen.route)
                }

                is ResourceState.ERROR -> {
                    Timber.d(response.error)
                }

                is ResourceState.LOADING -> {
                    Timber.d("Loading")
                }
            }
        }
    }

    private suspend fun postBooking(booking: Booking) {
        hairBookRepositoryBooking.bookHaircut(
            _accessToken.value, booking
        ).collectLatest { response ->
            when (response) {
                is ResourceState.SUCCESS -> {
                    Timber.d("bookHaircutIfPossible: ${response.data}")
                    _screen.emit(Routes.ViewShopScreen.route)
                }

                is ResourceState.ERROR -> {
                    Timber.d(response.error)
                }

                is ResourceState.LOADING -> {
                    Timber.d("Loading")
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getWorkingHours(): List<String> {
        val formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT)
        val selectedDate = LocalDate.parse(selectedDate.value, formatter)
        val dayOfWeek = selectedDate.dayOfWeek
        val shop = shop.value
        return when (dayOfWeek) {
            DayOfWeek.SUNDAY -> shop?.sundayHours ?: emptyList()
            DayOfWeek.MONDAY -> shop?.mondayHours ?: emptyList()
            DayOfWeek.TUESDAY -> shop?.tuesdayHours ?: emptyList()
            DayOfWeek.WEDNESDAY -> shop?.wednesdayHours ?: emptyList()
            DayOfWeek.THURSDAY -> shop?.thursdayHours ?: emptyList()
            DayOfWeek.FRIDAY -> shop?.fridayHours ?: emptyList()
            DayOfWeek.SATURDAY -> shop?.saturdayHours ?: emptyList()
        }
    }

    private fun getShopById() {
        viewModelScope.launch(Dispatchers.IO) {
            hairBookRepositoryCustomer.getShopById(_accessToken.value, _barberShopId.value)
                .collectLatest { response ->
                    when (response) {
                        is ResourceState.SUCCESS -> {
                            _shop.emit(response.data)
                            Timber.d("getShopById: ${_shop.value}")
                            getAllServicesByBarberShop()
                        }

                        is ResourceState.ERROR -> {
                            Timber.d(response.error)
                        }

                        is ResourceState.LOADING -> {
                            Timber.d("Loading")
                        }
                    }
                }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getDisabledDates(): List<LocalDate> {
        val daysOfWeek = listOf(
            DayOfWeek.SUNDAY,
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY,
            DayOfWeek.SATURDAY
        )
        val workingDays = shop.value?.workingDays?.mapIndexedNotNull { index, isWorkingDay ->
            if (isWorkingDay == 1f) daysOfWeek[index] else null
        }
        val disabledDates = mutableListOf<LocalDate>()
        var date = LocalDate.now()
        while (date.year == LocalDate.now().year) {
            if (workingDays != null) {
                if (!workingDays.contains(date.dayOfWeek)) {
                    disabledDates.add(date)
                }
            }
            date = date.plusDays(1)
        }
        return disabledDates
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

    fun profileClicked() {
        if (_role.value == Constants.BarberRole)
            _screen.value = Routes.BarberDetailsScreen.route
        else
            _screen.value = Routes.CustomerDetailsScreen.route
    }

    private fun sendMessage(message: String) {
        viewModelScope.launch {
            _toastMessage.emit(message)
        }
    }
}

