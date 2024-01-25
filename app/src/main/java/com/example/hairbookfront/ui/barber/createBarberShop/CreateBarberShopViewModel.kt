package com.example.hairbookfront.ui.barber.createBarberShop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hairbookfront.data.datastore.DataStorePreferences
import com.example.hairbookfront.domain.entities.BarberShop
import com.example.hairbookfront.domain.repository.ApiRepositoryBarber
import com.example.hairbookfront.ui.navgraph.Routes
import com.example.hairbookfront.util.ResourceState
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.regex.Pattern
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class CreateBarberShopViewModel @Inject constructor(
    private val hairBookRepositoryBarber: ApiRepositoryBarber,
    private val dataStorePreferences: DataStorePreferences,
) : ViewModel() {

    private val _barberShopName = MutableStateFlow("")
    val barberShopName: StateFlow<String>
        get() = _barberShopName

    private val _barberShopAddress = MutableStateFlow("")
    val barberShopAddress: StateFlow<String>
        get() = _barberShopAddress

    private val _barberShopPhoneNumber = MutableStateFlow("")
    val barberShopPhoneNumber: StateFlow<String>
        get() = _barberShopPhoneNumber

    private val _barberShopDescription = MutableStateFlow("")
    val barberShopDescription: StateFlow<String>
        get() = _barberShopDescription

    private val _daysOfWeek =
        MutableStateFlow(listOf(false, false, false, false, false, false, false))
    val daysOfWeek: StateFlow<List<Boolean>>
        get() = _daysOfWeek

    private val _hoursOfWeek = MutableStateFlow(List(7) { Pair("", "") })

    val days = listOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")

    private val _startTimeSunday = MutableStateFlow("")
    private val _endTimeSunday = MutableStateFlow("")
    private val _startTimeMonday = MutableStateFlow("")
    private val _endTimeMonday = MutableStateFlow("")
    private val _startTimeTuesday = MutableStateFlow("")
    private val _endTimeTuesday = MutableStateFlow("")
    private val _startTimeWednesday = MutableStateFlow("")
    private val _endTimeWednesday = MutableStateFlow("")
    private val _startTimeThursday = MutableStateFlow("")
    private val _endTimeThursday = MutableStateFlow("")
    private val _startTimeFriday = MutableStateFlow("")
    private val _endTimeFriday = MutableStateFlow("")
    private val _startTimeSaturday = MutableStateFlow("")
    private val _endTimeSaturday = MutableStateFlow("")

    private val _homeScreen = MutableStateFlow("")
    val homeScreen: StateFlow<String>
        get() = _homeScreen

    val setStartTimeFunctions = listOf(
        { time: String -> setStartTimeSunday(time) },
        { time: String -> setStartTimeMonday(time) },
        { time: String -> setStartTimeTuesday(time) },
        { time: String -> setStartTimeWednesday(time) },
        { time: String -> setStartTimeThursday(time) },
        { time: String -> setStartTimeFriday(time) },
        { time: String -> setStartTimeSaturday(time) }
    )

    val setEndTimeFunctions = listOf(
        { time: String -> setEndTimeSunday(time) },
        { time: String -> setEndTimeMonday(time) },
        { time: String -> setEndTimeTuesday(time) },
        { time: String -> setEndTimeWednesday(time) },
        { time: String -> setEndTimeThursday(time) },
        { time: String -> setEndTimeFriday(time) },
        { time: String -> setEndTimeSaturday(time) }
    )

    val timesOfWeek = listOf(
        Pair(_startTimeSunday, _endTimeSunday),
        Pair(_startTimeMonday, _endTimeMonday),
        Pair(_startTimeTuesday, _endTimeTuesday),
        Pair(_startTimeWednesday, _endTimeWednesday),
        Pair(_startTimeThursday, _endTimeThursday),
        Pair(_startTimeFriday, _endTimeFriday),
        Pair(_startTimeSaturday, _endTimeSaturday)
    )

    private val _shownClocks = MutableStateFlow(List(7) { false })

    val shownClocks: StateFlow<List<Boolean>>
        get() = _shownClocks

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    fun setShownClocks(index: Int, isShown: Boolean) {
        _shownClocks.value = _shownClocks.value.toMutableList().apply { set(index, isShown) }
    }


    fun setBarberShopName(it: String) {
        _barberShopName.value = it
    }

    fun setBarberShopDescription(it: String) {
        _barberShopDescription.value = it
    }

    fun setBarberShopAddress(it: String) {
        _barberShopAddress.value = it
    }

    fun setPhoneNumber(it: String) {
        _barberShopPhoneNumber.value = it
    }

    fun setDayOfWeek(index: Int, isChecked: Boolean) {
        _daysOfWeek.value = _daysOfWeek.value.toMutableList().apply { set(index, isChecked) }
    }

    private fun setStartTimeSunday(time: String) {
        _startTimeSunday.value = time
    }

    private fun setEndTimeSunday(time: String) {
        _endTimeSunday.value = time
    }

    private fun setStartTimeMonday(time: String) {
        _startTimeMonday.value = time
    }

    private fun setEndTimeMonday(time: String) {
        _endTimeMonday.value = time
    }

    private fun setStartTimeTuesday(time: String) {
        _startTimeTuesday.value = time
    }

    private fun setEndTimeTuesday(time: String) {
        _endTimeTuesday.value = time
    }

    private fun setStartTimeWednesday(time: String) {
        _startTimeWednesday.value = time
    }

    private fun setEndTimeWednesday(time: String) {
        _endTimeWednesday.value = time
    }

    private fun setStartTimeThursday(time: String) {
        _startTimeThursday.value = time
    }

    private fun setEndTimeThursday(time: String) {
        _endTimeThursday.value = time
    }

    private fun setStartTimeFriday(time: String) {
        _startTimeFriday.value = time
    }

    private fun setEndTimeFriday(time: String) {
        _endTimeFriday.value = time
    }

    private fun setStartTimeSaturday(time: String) {
        _startTimeSaturday.value = time
    }

    private fun setEndTimeSaturday(time: String) {
        _endTimeSaturday.value = time
    }

    private val _barberShopNameError = MutableStateFlow(false)
    val barberShopNameError: StateFlow<Boolean>
        get() = _barberShopNameError

    private val _barberShopAddressError = MutableStateFlow(false)
    val barberShopAddressError: StateFlow<Boolean>
        get() = _barberShopAddressError

    private val _barberShopPhoneNumberError = MutableStateFlow(false)
    val barberShopPhoneNumberError: StateFlow<Boolean>
        get() = _barberShopPhoneNumberError

    private val _barberShopDescriptionError = MutableStateFlow(false)
    val barberShopDescriptionError: StateFlow<Boolean>
        get() = _barberShopDescriptionError


    fun isValidInput() {
        when {
            !isValidBarberShopName() -> {
                sendMessage("Please fill the Barber Shop Name field")
            }

            !isValidBarberShopDescription() -> {
                sendMessage("Please fill the Barber Shop Description field")
            }

            !isValidBarberShopAddress() -> {
                sendMessage("Please fill the Barber Shop Address field")
            }

            !isPhoneNumberValid() -> {
                sendMessage("Please enter a valid Phone Number")
            }

            !isValidDaysOfWeek() -> {
                sendMessage("Please select at least one day of the week")
            }

            !isValidWorkingHours() -> {
                sendMessage("Start and end time need to be different")
            }

            else -> {
                createBarberShop()
                sendMessage("All inputs are valid")
            }
        }
    }

    private fun isValidWorkingHours(): Boolean {
        val workingHours = listOf(
            Pair(_startTimeSunday.value, _endTimeSunday.value),
            Pair(_startTimeMonday.value, _endTimeMonday.value),
            Pair(_startTimeTuesday.value, _endTimeTuesday.value),
            Pair(_startTimeWednesday.value, _endTimeWednesday.value),
            Pair(_startTimeThursday.value, _endTimeThursday.value),
            Pair(_startTimeFriday.value, _endTimeFriday.value),
            Pair(_startTimeSaturday.value, _endTimeSaturday.value)
        )

        return workingHours.all { (start, end) ->
            if (start.isEmpty() && end.isEmpty()) {
                true
            } else {
                start != end
            }
        }
    }

    private fun createBarberShop() {
        viewModelScope.launch {
            val barberName = getBarberName()
            val workingDays = _daysOfWeek.value.map { if (it) 1.0f else 0.0f }
            val sundayHours = if (_daysOfWeek.value[0]) generateWorkingHours(
                _startTimeSunday.value,
                _endTimeSunday.value
            ) else listOf()
            val mondayHours = if (_daysOfWeek.value[1]) generateWorkingHours(
                _startTimeMonday.value,
                _endTimeMonday.value
            ) else listOf()
            val tuesdayHours = if (_daysOfWeek.value[2]) generateWorkingHours(
                _startTimeTuesday.value,
                _endTimeTuesday.value
            ) else listOf()
            val wednesdayHours = if (_daysOfWeek.value[3]) generateWorkingHours(
                _startTimeWednesday.value,
                _endTimeWednesday.value
            ) else listOf()
            val thursdayHours = if (_daysOfWeek.value[4]) generateWorkingHours(
                _startTimeThursday.value,
                _endTimeThursday.value
            ) else listOf()
            val fridayHours = if (_daysOfWeek.value[5]) generateWorkingHours(
                _startTimeFriday.value,
                _endTimeFriday.value
            ) else listOf()
            val saturdayHours = if (_daysOfWeek.value[6]) generateWorkingHours(
                _startTimeSaturday.value,
                _endTimeSaturday.value
            ) else listOf()
            val barberShop = BarberShop(
                barbershopId = null,
                barbershopName = _barberShopName.value,
                barberName = barberName,
                phoneNumber = _barberShopPhoneNumber.value,
                workingDays = workingDays,
                sundayHours = sundayHours,
                mondayHours = mondayHours,
                tuesdayHours = tuesdayHours,
                wednesdayHours = wednesdayHours,
                thursdayHours = thursdayHours,
                fridayHours = fridayHours,
                saturdayHours = saturdayHours,
                totalRating = 0.0f,
                location = _barberShopAddress.value,
                description = _barberShopDescription.value
            )
            Timber.d("Barber Shop: $barberShop")
            val accessToken = dataStorePreferences.getAccessToken().first()
            hairBookRepositoryBarber.createBarberShop(accessToken, barberShop).collectLatest {
                when (it) {
                    is ResourceState.LOADING -> {
                        Timber.d("Loading")
                    }

                    is ResourceState.SUCCESS -> {
                        _homeScreen.emit(Routes.BarberDetailsScreen.route)
                        Timber.d("Success")
                    }

                    is ResourceState.ERROR -> {
                        Timber.d("Error")
                    }
                }
            }

        }
    }

    private fun generateWorkingHours(startTime: String, endTime: String): List<String> {

        Timber.d("startTime: $startTime")
        Timber.d("endTime: $endTime")
        val startHour = startTime.split(":")[0].toInt()
        val startMinute = startTime.split(":")[1].toInt()
        val endHour = endTime.split(":")[0].toInt()
        val endMinute = endTime.split(":")[1].toInt()

        val start = startHour * 60 + startMinute
        val end = endHour * 60 + endMinute

        val workingHours = mutableListOf<String>()

        for (minute in start until end step 30) {
            val hour = minute / 60
            val min = minute % 60
            val time = String.format("%02d:%02d", hour, min)
            workingHours.add(time)
        }
        return workingHours
    }

    suspend fun getBarberName(): String {
        val firstName = dataStorePreferences.getFirstName().first()
        val lastName = dataStorePreferences.getLastName().first()
        return "$firstName $lastName"
    }

    private fun isValidBarberShopName(): Boolean {
        val isValid = _barberShopName.value.isNotBlank()
        _barberShopNameError.value = !isValid
        return isValid
    }

    private fun isValidBarberShopDescription(): Boolean {
        val isValid = _barberShopDescription.value.isNotBlank()
        _barberShopDescriptionError.value = !isValid
        return isValid
    }

    private fun isValidBarberShopAddress(): Boolean {
        val isValid = _barberShopAddress.value.isNotBlank()
        _barberShopAddressError.value = !isValid
        return isValid
    }

    private fun isPhoneNumberValid(): Boolean {
        val phoneNumberRegex = "^\\d{10}\$"
        val pattern = Pattern.compile(phoneNumberRegex)
        val matcher = pattern.matcher(_barberShopPhoneNumber.value)
        _barberShopPhoneNumberError.value = !matcher.matches()
        return matcher.matches()
    }

    private fun isValidDaysOfWeek(): Boolean {
        return _daysOfWeek.value.any { it }
    }

    private fun sendMessage(message: String) {
        viewModelScope.launch {
            _toastMessage.emit(message)
        }
    }
}