package com.example.hairbookfront.ui.mainActivity

import androidx.lifecycle.ViewModel
import com.example.hairbookfront.ui.navgraph.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.example.hairbookfront.data.datastore.DataStorePreferences
import com.example.hairbookfront.util.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * ViewModel for the MainActivity.
 *
 * @property dataStorePreferences The datastore for storing preferences.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataStorePreferences: DataStorePreferences
) : ViewModel() {

    private val _accessToken = MutableStateFlow("")
    private val _role = MutableStateFlow("")

    private val _isSplashScreenVisible = MutableStateFlow(true)
    val isSplashScreenVisible: StateFlow<Boolean>
        get() = _isSplashScreenVisible


    private val _startDestination = MutableStateFlow("")
    val startDestination: StateFlow<String>
        get() = _startDestination

    init {
        viewModelScope.launch {
            _accessToken.emit(dataStorePreferences.getAccessToken().first())
            if (_accessToken.value.isNotEmpty()) {
                _role.emit(dataStorePreferences.getRole().first())
                if (_role.value == Constants.CustomerRole)
                    _startDestination.emit(Routes.CustomerGraph.route)
                else if (_role.value == Constants.BarberRole)
                    _startDestination.emit(Routes.BarberGraph.route)
            } else {
                _startDestination.emit(Routes.AuthGraph.route)
            }
            _isSplashScreenVisible.emit(false)
        }

    }
}