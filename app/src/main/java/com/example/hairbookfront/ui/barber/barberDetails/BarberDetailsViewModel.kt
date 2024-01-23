package com.example.hairbookfront.ui.barber.barberDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hairbookfront.data.datastore.DataStorePreferences
import com.example.hairbookfront.domain.entities.BarberShop
import com.example.hairbookfront.domain.repository.ApiRepositoryAuth
import com.example.hairbookfront.util.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BarberDetailsViewModel @Inject constructor(
    private val dataStorePreferences: DataStorePreferences,
    private val apiRepository: ApiRepositoryAuth
) : ViewModel() {
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

    init {
        viewModelScope.launch {
            dataStorePreferences.getAccessToken().collectLatest { accessToken ->
                apiRepository.getMyBarberShops("Bearer $accessToken").collectLatest { response ->
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
            dataStorePreferences.getFirstName().collectLatest { it ->
                _firstName.emit(it)
            }
            dataStorePreferences.getLastName().collectLatest { it ->
                _lastName.emit(it)
            }
            dataStorePreferences.getYearsOfExperience().collectLatest { it ->
                _exp.emit(it)
            }
            dataStorePreferences.getEmail().collectLatest { it ->
                _email.emit(it)
            }

        }
    }

}