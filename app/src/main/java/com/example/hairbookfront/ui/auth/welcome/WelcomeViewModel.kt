package com.example.hairbookfront.ui.auth.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.hairbookfront.domain.repository.ApiRepository
import com.example.hairbookfront.ui.navgraph.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val hairBookRepository: ApiRepository
) : ViewModel() {

    private val _email = MutableStateFlow("chen24201@gmail.com")
    val email: StateFlow<String>
        get() = _email
    private val _password = MutableStateFlow("12345678")
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

    private val _loggedIn = MutableStateFlow(false)
    val loggedIn: StateFlow<Boolean>
        get() = _loggedIn

    fun sendMessage(message: String) {
        viewModelScope.launch {
            _toastMessage.emit(message)
        }
    }

    fun emailChanged(email: String) {
        _email.value = email
    }

    fun passwordChanged(password: String) {
        _password.value = password
    }

    fun showOrHidePassword() {
        _showOrHidePassword.value = !_showOrHidePassword.value
    }

    private fun isValidEmail(): Boolean {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        val pattern = Pattern.compile(emailRegex)
        val matcher = pattern.matcher(email.value)
        return matcher.matches()
    }

    private fun isValidPassword(): Boolean {
        val passwordRegex = "^.{8,}$"
        val pattern = Pattern.compile(passwordRegex)
        val matcher = pattern.matcher(password.value)
        return matcher.matches()
    }


    suspend fun login() {
        viewModelScope.launch(Dispatchers.IO) {
            if (isValidEmail() && isValidPassword()) {
                _emailError.value = false
                _passwordError.value = false
                Timber.d("email: ${email.value}, password: ${password.value}")
                _loggedIn.value = true
            } else {
                if (!isValidEmail()) {
                    sendMessage("Invalid Email")
                    _emailError.value = true
                }
                if (!isValidPassword()) {
                    sendMessage("Invalid Password")
                    _passwordError.value = true
                }
            }
        }
    }

}