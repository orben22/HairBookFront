package com.example.hairbookfront.ui.barber.editOrCreateBarberShop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hairbookfront.data.datastore.DataStorePreferences
import com.example.hairbookfront.domain.SignOutHandler
import com.example.hairbookfront.domain.entities.BarberShop
import com.example.hairbookfront.domain.entities.Service
import com.example.hairbookfront.domain.repository.ApiRepositoryBarber
import com.example.hairbookfront.ui.navgraph.Routes
import com.example.hairbookfront.util.Constants
import com.example.hairbookfront.util.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
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

/**
 * ViewModel for the EditOrCreateBarberShop screen.
 *
 * @property hairBookRepositoryBarber The repository for barber related operations.
 * @property dataStorePreferences The datastore for storing preferences.
 */
@HiltViewModel
class EditOrCreateBarberShopViewModel @Inject constructor(
    private val hairBookRepositoryBarber: ApiRepositoryBarber,
    private val dataStorePreferences: DataStorePreferences,
    private val signOutHandler: SignOutHandler
) : ViewModel() {

    private val _shopId = MutableStateFlow("")
    private val _mode = MutableStateFlow("")
    val mode: StateFlow<String>
        get() = _mode
    private val _accessToken = MutableStateFlow("")
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

    private val _screen = MutableStateFlow("")
    val screen: StateFlow<String>
        get() = _screen

    private val _lastScreen = MutableStateFlow(false)
    val lastScreen: StateFlow<Boolean>
        get() = _lastScreen

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

    init {
        viewModelScope.launch {
            _accessToken.emit(dataStorePreferences.getAccessToken().first())
            _mode.emit(dataStorePreferences.getMode().first())
            if (_mode.value == Constants.EditMode) {
                _shopId.emit(dataStorePreferences.getShopId().first())
                getBarberShop()
            }
        }
    }

    fun clearScreen(){
        _screen.value = ""
    }
    fun onBackClicked() {
        _lastScreen.value = true
    }

    private suspend fun getBarberShop() {
        hairBookRepositoryBarber.getBarberShopById(_accessToken.value, _shopId.value)
            .collectLatest { response ->
                when (response) {
                    is ResourceState.LOADING -> {
                        Timber.d("Loading")
                    }

                    is ResourceState.SUCCESS -> {
                        val barberShop = response.data
                        setBarberShopName(barberShop.barberShopName)
                        setBarberShopDescription(barberShop.description)
                        setBarberShopAddress(barberShop.location)
                        setPhoneNumber(barberShop.phoneNumber)
                        val workingDays = barberShop.workingDays.map { it == 1.0f }
                        _daysOfWeek.emit(workingDays)
                        initWorkingHoursForAllDays(barberShop)
                        loadServices()
                    }

                    is ResourceState.ERROR -> {
                        Timber.d("Error")
                    }
                }
            }
    }

    private fun initWorkingHoursForAllDays(barberShop: BarberShop) {
        val daysOfWeek = listOf(
            barberShop.sundayHours,
            barberShop.mondayHours,
            barberShop.tuesdayHours,
            barberShop.wednesdayHours,
            barberShop.thursdayHours,
            barberShop.fridayHours,
            barberShop.saturdayHours
        )

        val startTimes = listOf(
            _startTimeSunday,
            _startTimeMonday,
            _startTimeTuesday,
            _startTimeWednesday,
            _startTimeThursday,
            _startTimeFriday,
            _startTimeSaturday
        )

        val endTimes = listOf(
            _endTimeSunday,
            _endTimeMonday,
            _endTimeTuesday,
            _endTimeWednesday,
            _endTimeThursday,
            _endTimeFriday,
            _endTimeSaturday
        )

        for (i in daysOfWeek.indices) {
            val hours = daysOfWeek[i]
            if (!hours.isNullOrEmpty()) {
                viewModelScope.launch {
                    startTimes[i].emit(hours.first())
                    val endTime = hours.last()
                    val endTimeSplit = endTime.split(":").map { it.toInt() }
                    var endHour = endTimeSplit[0]
                    var endMinute = endTimeSplit[1] + 30

                    if (endMinute >= 60) {
                        endHour += 1
                        endMinute -= 60
                    }
                    endTimes[i].emit(String.format("%02d:%02d", endHour, endMinute))
                    setShownClocks(i, true)
                }
            } else {
                viewModelScope.launch {
                    startTimes[i].emit("")
                    endTimes[i].emit("")

                }
            }
        }
    }

    private suspend fun loadServices() {
        hairBookRepositoryBarber.getServices(_accessToken.value, _shopId.value)
            .collectLatest {
                when (it) {
                    is ResourceState.LOADING -> {
                        Timber.d("Loading")
                    }

                    is ResourceState.SUCCESS -> {
                        _services.emit(it.data)
                    }

                    is ResourceState.ERROR -> {
                        Timber.d("Error")
                    }
                }
            }
    }

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

    private val _services = MutableStateFlow<List<Service>>(listOf())
    val services: StateFlow<List<Service>>
        get() = _services

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean>
        get() = _showDialog

    private val _serviceName = MutableStateFlow("")
    val serviceName: StateFlow<String>
        get() = _serviceName

    private val _servicePrice = MutableStateFlow("")
    val servicePrice: StateFlow<String>
        get() = _servicePrice

    private val _serviceDuration = MutableStateFlow("")
    val serviceDuration: StateFlow<String>
        get() = _serviceDuration

    fun setServiceName(it: String) {
        _serviceName.value = it
    }

    fun setServicePrice(it: String) {
        _servicePrice.value = it
    }

    fun setServiceDuration(it: String) {
        _serviceDuration.value = it
    }

    private val _serviceNameError = MutableStateFlow(false)
    val serviceNameError: StateFlow<Boolean>
        get() = _serviceNameError
    private val _serviceDurationError = MutableStateFlow(false)
    val serviceDurationError: StateFlow<Boolean>
        get() = _serviceDurationError
    private val _servicePriceError = MutableStateFlow(false)
    val servicePriceError: StateFlow<Boolean>
        get() = _servicePriceError

    private val _editingService = MutableStateFlow<Service?>(null)
    val editingService: StateFlow<Service?>
        get() = _editingService

    fun onEditClicked(service: Service) {
        _serviceName.value = service.serviceName
        _servicePrice.value = service.price.toString()
        _serviceDuration.value = service.duration.toString()
        Timber.d("onEditClicked service: $service")
        _editingService.value = service
    }


    fun onDeleteServiceClicked(serviceId: String) {
        val serviceToRemove = _services.value.find { it.serviceId == serviceId }

        if (serviceToRemove != null) {
            _services.value = _services.value.toMutableList().apply { remove(serviceToRemove) }
        }
    }

    private var serviceIdCounter = MutableStateFlow(0)

    private fun addService() {
        val serviceName = _serviceName.value
        val servicePrice = _servicePrice.value
        val serviceDuration = _serviceDuration.value
        val barberShopId = "1"
        val serviceId = serviceIdCounter.value++.toString()
        val service = Service(
            serviceName = serviceName,
            price = servicePrice.toFloat(),
            duration = serviceDuration.toFloat(),
            barberShopId = barberShopId,
            serviceId = serviceId
        )
        Timber.d("Service: $service")
        _services.value = _services.value.toMutableList().apply { add(service) }
        _serviceName.value = ""
        _servicePrice.value = ""
        _serviceDuration.value = ""
        _showDialog.value = false
    }

    fun onAcceptClicked(service: Service) {
        service.serviceName = _serviceName.value
        service.price = _servicePrice.value.toFloat()
        service.duration = _serviceDuration.value.toFloat()
        _editingService.value = null
    }

    fun validateServiceInput() {
        Timber.d(
            "validateServiceInput _serviceName.value: ${_serviceName.value} " +
                    "_servicePrice.value: ${_servicePrice.value} " +
                    "_serviceDuration.value: ${_serviceDuration.value}"
        )
        if (_serviceName.value.isEmpty()) {
            _serviceNameError.value = true
        }
        if (_servicePrice.value.isEmpty()) {
            _servicePriceError.value = true
        }
        if (_serviceDuration.value.isEmpty()) {
            _serviceDurationError.value = true
        }
        if (_serviceName.value.isNotEmpty() && _servicePrice.value.isNotEmpty() && _serviceDuration.value.isNotEmpty()) {
            addService()
        }
    }

    fun onDismiss() {
        _showDialog.value = false
    }

    fun onButtonClick() {
        _showDialog.value = true
    }


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

            _services.value.isEmpty() -> {
                sendMessage("Please add at least one service")
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
                barberShopId = null,
                barberShopName = _barberShopName.value,
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
                totalRating = 5.0f,
                location = _barberShopAddress.value,
                description = _barberShopDescription.value
            )
            Timber.d("Barber Shop: $barberShop")
            if (_mode.value == Constants.EditMode) {
                updateBarberShop(barberShop)
            } else {
                postBarberShop(barberShop)
            }
        }
    }

    private suspend fun updateBarberShop(barberShop: BarberShop) {
        barberShop.barberShopId = _shopId.value
        hairBookRepositoryBarber.updateBarberShop(_accessToken.value, _shopId.value, barberShop)
            .collectLatest {
                when (it) {
                    is ResourceState.LOADING -> {
                        Timber.d("Loading")
                    }

                    is ResourceState.SUCCESS -> {
                        postOrUpdateServices()
                    }

                    is ResourceState.ERROR -> {
                        Timber.d("Error")
                    }
                }
            }
    }

    private suspend fun postOrUpdateServices() {
        for (service in _services.value) {
            if (service.serviceId == null) {
                hairBookRepositoryBarber.createService(_accessToken.value, _shopId.value, service)
                    .collectLatest {
                        when (it) {
                            is ResourceState.LOADING -> {
                                Timber.d("Loading")
                            }

                            is ResourceState.SUCCESS -> {
                                Timber.d("Success")
                            }

                            is ResourceState.ERROR -> {
                                Timber.d("Error")
                            }
                        }
                    }
            } else {
                hairBookRepositoryBarber.updateService(
                    _accessToken.value,
                    _shopId.value,
                    service.serviceId,
                    service
                )
                    .collectLatest {
                        when (it) {
                            is ResourceState.LOADING -> {
                                Timber.d("Loading")
                            }

                            is ResourceState.SUCCESS -> {
                                Timber.d("Success")
                            }

                            is ResourceState.ERROR -> {
                                Timber.d("Error")
                            }
                        }
                    }
            }

        }
        _screen.emit(Routes.BarberDetailsScreen.route)
    }

    private suspend fun postBarberShop(barberShop: BarberShop) {
        hairBookRepositoryBarber.createBarberShop(_accessToken.value, barberShop)
            .collectLatest {
                when (it) {
                    is ResourceState.LOADING -> {
                        Timber.d("Loading")
                    }

                    is ResourceState.SUCCESS -> {
                        _shopId.emit(it.data.barberShopId.toString())
                        postOrUpdateServices()
                        Timber.d("Success")
                    }

                    is ResourceState.ERROR -> {
                        Timber.d("Error")
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