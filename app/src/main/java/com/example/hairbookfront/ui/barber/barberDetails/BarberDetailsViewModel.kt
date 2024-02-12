package com.example.hairbookfront.ui.barber.barberDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hairbookfront.data.datastore.DataStorePreferences
import com.example.hairbookfront.domain.SignOutHandler
import com.example.hairbookfront.domain.entities.BarberShop
import com.example.hairbookfront.domain.repository.ApiRepositoryBarber
import com.example.hairbookfront.ui.navgraph.Routes
import com.example.hairbookfront.util.Constants
import com.example.hairbookfront.util.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * ViewModel for the BarberDetails screen.
 *
 * @property signOutHandler The handler for signing out.
 * @property dataStorePreferences The datastore for storing preferences.
 * @property apiRepositoryBarber The repository for barber related operations.
 */
@HiltViewModel
class BarberDetailsViewModel @Inject constructor(
    private val signOutHandler: SignOutHandler,
    private val dataStorePreferences: DataStorePreferences,
    private val apiRepositoryBarber: ApiRepositoryBarber,
) : ViewModel() {

    private val _accessToken = MutableStateFlow("")
    val accessToken: StateFlow<String>
        get() = _accessToken

    private val _showOrHideDeleteDialog = MutableStateFlow(false)
    val showOrHideDeleteDialog: StateFlow<Boolean>
        get() = _showOrHideDeleteDialog

    private val _isExpanded = MutableStateFlow(false)
    val isExpanded: StateFlow<Boolean>
        get() = _isExpanded

    private val _screen = MutableStateFlow("")
    val screen: StateFlow<String>
        get() = _screen

    private val _lastScreen = MutableStateFlow(false)
    val lastScreen: StateFlow<Boolean>
        get() = _lastScreen

    private val _firstName = MutableStateFlow("")
    val firstName: StateFlow<String>
        get() = _firstName
    private val _lastName = MutableStateFlow("")
    val lastName: StateFlow<String>
        get() = _lastName

    private val _email = MutableStateFlow("")
    val email: StateFlow<String>
        get() = _email

    private val _myShops = MutableStateFlow(listOf<BarberShop>())
    val myShops: StateFlow<List<BarberShop>>
        get() = _myShops

    private val _shopToDelete = MutableStateFlow("")
    fun getYearsOfExperience(): Flow<Int> {
        return dataStorePreferences.getYearsOfExperience()
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
    fun clearScreen(){
        _screen.value = ""
    }
    fun onBackClicked() {
        _lastScreen.value = true
    }

    fun onBarberShopClicked(barberShop: BarberShop) {
        viewModelScope.launch {
            dataStorePreferences.setShopId(barberShop.barberShopId!!)
            _screen.emit(Routes.ViewShopScreen.route)
        }
    }

    fun onCreateBarberShopClicked() {
        _screen.value = Routes.EditOrCreateBarberShopScreen.route
    }

    fun onMyBookingClicked() {
        _screen.value = Routes.MyBookingsScreen.route
    }

    fun onDismissRequest() {
        _showOrHideDeleteDialog.value = false
    }

    fun showOrHideDeleteDialog(barberShopId: String) {
        _shopToDelete.value = barberShopId
        _showOrHideDeleteDialog.value = !showOrHideDeleteDialog.value
    }

    fun editShop(barberShopId: String) {
        viewModelScope.launch {
            dataStorePreferences.setShopId(barberShopId)
            dataStorePreferences.setMode(Constants.EditMode)
            _screen.emit(Routes.EditOrCreateBarberShopScreen.route)
        }
    }

    fun deleteShop() {
        viewModelScope.launch {
            apiRepositoryBarber.deleteBarberShop(_accessToken.value, _shopToDelete.value)
                .collectLatest { response ->
                    when (response) {
                        is ResourceState.LOADING -> {
                            Timber.d("Loading")
                        }

                        is ResourceState.SUCCESS -> {
                            Timber.d("Success")
                            _showOrHideDeleteDialog.value = false
                            _myShops.value=_myShops.value.filter { it.barberShopId != _shopToDelete.value }
                            getMyShops()
                        }

                        is ResourceState.ERROR -> {
                            Timber.d("Error")
                        }
                    }
                }
        }
    }

    init {
        refreshData()
    }

    fun refreshData() {
        viewModelScope.launch {
            _accessToken.emit(dataStorePreferences.getAccessToken().first())
            getMyShops()
        }
    }

    private suspend fun getMyShops() {
        apiRepositoryBarber.getMyBarberShops(_accessToken.value)
            .collectLatest { response ->
                Timber.d("response: $response")
                when (response) {
                    is ResourceState.LOADING -> {
                        Timber.d("Loading")
                    }

                    is ResourceState.SUCCESS -> {
                        Timber.d("Success")
                        _myShops.emit(response.data)
                    }

                    is ResourceState.ERROR -> {
                        Timber.d("Error")
                    }
                }
            }
    }
}