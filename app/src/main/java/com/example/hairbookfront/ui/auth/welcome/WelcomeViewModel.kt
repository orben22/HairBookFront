package com.example.hairbookfront.ui.auth.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hairbookfront.data.datastore.DataStorePreferences
import com.example.hairbookfront.domain.entities.User
import com.example.hairbookfront.domain.repository.ApiRepositoryAuth
import com.example.hairbookfront.ui.navgraph.Routes
import com.example.hairbookfront.util.Constants.BarberRole
import com.example.hairbookfront.util.Constants.CustomerRole
import com.example.hairbookfront.util.ResourceState
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val hairBookRepository: ApiRepositoryAuth,
    private val dataStorePreferences: DataStorePreferences,
) : ViewModel() {

    //customer@customer.com customer_password
    // barber@example.com barber_password
    private val _email = MutableStateFlow("barber@example.com")
    val email: StateFlow<String>
        get() = _email
    private val _password = MutableStateFlow("barber_password")
    val password: StateFlow<String>
        get() = _password

    private val _emailError = MutableStateFlow(false)
    val emailError: StateFlow<Boolean>
        get() = _emailError

    private val _passwordError = MutableStateFlow(false)
    val passwordError: StateFlow<Boolean>
        get() = _passwordError

    private val _showOrHidePassword = MutableStateFlow(false)
    val showOrHidePassword: StateFlow<Boolean>
        get() = _showOrHidePassword

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()


    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean>
        get() = _showDialog

    private val _dialogText = MutableStateFlow(listOf(CustomerRole, BarberRole))
    val dialogText: StateFlow<List<String>>
        get() = _dialogText


    private val _userDetails: MutableStateFlow<User?> =
        MutableStateFlow(null)
    val userDetails: StateFlow<User?>
        get() = _userDetails


    private val _signUpScreen = MutableStateFlow("")
    val signUpScreen: StateFlow<String>
        get() = _signUpScreen

    private val _homeScreen = MutableStateFlow("")
    val homeScreen: StateFlow<String>
        get() = _homeScreen

    fun emailChanged(email: String) {
        _email.value = email
    }

    fun passwordChanged(password: String) {
        _password.value = password
    }

    fun showOrHidePassword() {
        _showOrHidePassword.value = !_showOrHidePassword.value
    }

    fun showOrHideDialog() {
        _showDialog.value = !_showDialog.value
    }

    fun signUpBarberClicked() {
        _signUpScreen.value = Routes.SignupBarberScreen.route
        _showDialog.value = false
    }

    fun signUpCustomerClicked() {
        _signUpScreen.value = Routes.SignupCustomerScreen.route
        _showDialog.value = false
    }

    private fun sendMessage(message: String) {
        viewModelScope.launch {
            _toastMessage.emit(message)
        }
    }

    private fun isValidEmail(): Boolean {
        val emailRegex = "^[A-Za-z](.*)(@)(.+)(\\.)(.+)"
        val pattern = Pattern.compile(emailRegex)
        val matcher = pattern.matcher(_email.value)
        return matcher.matches()
    }

    private fun isValidPassword(): Boolean {
        val passwordRegex = "^.{8,}$"
        val pattern = Pattern.compile(passwordRegex)
        val matcher = pattern.matcher(_password.value)
        return matcher.matches()
    }


    fun login() {
        if (areCredentialsValid()) {
            viewModelScope.launch(Dispatchers.IO) {
                hairBookRepository.login(email.value, password.value).collectLatest { response ->
                    when (response) {
                        is ResourceState.SUCCESS -> handleLoginSuccess(response.data)
                        is ResourceState.ERROR -> handleLoginError(response.error)
                        else -> {}
                    }
                }
            }
        }
    }

    private fun areCredentialsValid(): Boolean {
        val isEmailValid = isValidEmail()
        val isPasswordValid = isValidPassword()

        if (!isEmailValid) {
            sendMessage("Invalid Email")
            _emailError.value = true
        }

        if (!isPasswordValid) {
            sendMessage("Invalid Password")
            _passwordError.value = true
        }

        return isEmailValid && isPasswordValid
    }

    private suspend fun handleLoginSuccess(data: User) {
        _userDetails.emit(data)
        storeUserDetails()
        if (data.role == CustomerRole) {
            hairBookRepository.getDetailsCustomer(data.accessToken!!).collect {
                when (it) {
                    is ResourceState.SUCCESS -> {
                        dataStorePreferences.storeCustomerDetails(it.data)
                        _homeScreen.value = Routes.MyBookingsScreen.route
                    }

                    is ResourceState.ERROR -> sendMessage(it.error)
                    else -> {}
                }
            }
        } else if (data.role == BarberRole) {
            hairBookRepository.getDetailsBarber(data.accessToken!!).collect {
                when (it) {
                    is ResourceState.SUCCESS -> {
                        dataStorePreferences.storeBarberDetails(it.data)
                        _homeScreen.value = Routes.CreateBarberShopScreen.route
                    }

                    is ResourceState.ERROR -> sendMessage(it.error)
                    else -> {}
                }
            }
        }
    }

    private fun handleLoginError(error: String) {
        sendMessage(error)
    }

    private suspend fun storeUserDetails() {
        dataStorePreferences.storeUserDetails(_userDetails.value!!)
    }
}
