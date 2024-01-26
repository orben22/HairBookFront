package com.example.hairbookfront.ui.shared.editOrCreateBooking

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hairbookfront.data.datastore.DataStorePreferences
import com.example.hairbookfront.domain.SignOutHandler
import com.example.hairbookfront.domain.entities.Service
import com.example.hairbookfront.domain.repository.ApiRepositoryBarber
import com.example.hairbookfront.domain.repository.ApiRepositoryBooking
import com.example.hairbookfront.ui.navgraph.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.DayOfWeek
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class EditOrCreateBookingViewModel @Inject constructor(
    private val signOutHandler: SignOutHandler,
    private val hairBookRepositoryBooking: ApiRepositoryBooking,
    private val hairBookRepositoryBarber: ApiRepositoryBarber,
    private val dataStorePreferences: DataStorePreferences,
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

    private val _selectedService = MutableStateFlow<Service?>(null)
    val selectedService: StateFlow<Service?>
        get() = _selectedService

    private val _selectedDate = MutableStateFlow("")
    val selectedDate: StateFlow<String>
        get() = _selectedDate

    private val _showTimeSlots = MutableStateFlow(false)
    val showTimeSlots: StateFlow<Boolean>
        get() = _showTimeSlots

    private val _selectedTimeSlot = MutableStateFlow("")
    val selectedTimeSlot: StateFlow<String>
        get() = _selectedTimeSlot

    fun onServiceSelected(service: Service) {
        _selectedService.value = service
    }

    fun onDateSelected(date: String) {
        _selectedDate.value= date
        Timber.d("onDateSelected: ${_selectedDate.value}")
        _showTimeSlots.value = true
    }

    fun onTimeSlotSelected(timeSlot: String) {
        _selectedTimeSlot.value = timeSlot
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getDisabledDates(
        workingDaysList: List<Int> = listOf(
            1,
            0,
            0,
            0,
            1,
            0,
            0
        )
    ): List<LocalDate> {
        val daysOfWeek = listOf(
            DayOfWeek.SUNDAY,
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY,
            DayOfWeek.SATURDAY
        )
        val workingDays = workingDaysList.mapIndexedNotNull { index, isWorkingDay ->
            if (isWorkingDay == 1) daysOfWeek[index] else null
        }
        val disabledDates = mutableListOf<LocalDate>()
        var date = LocalDate.now()
        while (date.year == LocalDate.now().year) {
            if (!workingDays.contains(date.dayOfWeek)) {
                disabledDates.add(date)
            }
            date = date.plusDays(1)
        }
        return disabledDates
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

}

