package com.example.hairbookfront.ui.barber.barberDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hairbookfront.data.datastore.DataStorePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BarberDetailsViewModel @Inject constructor(
    private val dataStorePreferences: DataStorePreferences,
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

    init {
        viewModelScope.launch {
            dataStorePreferences.getFirstName().collectLatest { it ->
                _firstName.value = it
            }
            dataStorePreferences.getLastName().collectLatest { it ->
                _lastName.value = it
            }
            dataStorePreferences.getYearsOfExperience().collectLatest { it ->
                _exp.value = it
            }
            dataStorePreferences.getEmail().collectLatest { it ->
                _email.value = it
            }
        }
    }

}