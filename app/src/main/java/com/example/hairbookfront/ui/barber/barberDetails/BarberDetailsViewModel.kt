package com.example.hairbookfront.ui.barber.barberDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hairbookfront.data.datastore.DataStorePreferences
import com.example.hairbookfront.domain.SignOutHandler
import com.example.hairbookfront.domain.entities.BarberShop
import com.example.hairbookfront.domain.repository.ApiRepositoryBarber
import com.example.hairbookfront.ui.navgraph.Routes
import com.example.hairbookfront.util.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BarberDetailsViewModel @Inject constructor(
    private val signOutHandler: SignOutHandler,
    private val dataStorePreferences: DataStorePreferences,
    private val apiRepositoryBarber: ApiRepositoryBarber,
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

    private val _exp = MutableStateFlow(0)
    val exp: StateFlow<Int>
        get() = _exp

    private val _email = MutableStateFlow("")
    val email: StateFlow<String>
        get() = _email

    private val _myshops = MutableStateFlow(listOf<BarberShop>())
    val myshops: StateFlow<List<BarberShop>>
        get() = _myshops

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
                Timber.d(accessToken)
                _accessToken.emit(accessToken)
                apiRepositoryBarber.getMyBarberShops(_accessToken.value)
                    .collectLatest { response ->
                        Timber.d("response: $response")
                        when (response) {
                            is ResourceState.LOADING -> {
                                Timber.d("Loading")
                            }

                            is ResourceState.SUCCESS -> {
                                Timber.d("Success")
                                _myshops.emit(response.data)
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