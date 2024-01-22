package com.example.hairbookfront.ui.auth.signUpBarber

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hairbookfront.di.DataStorePreferences
import com.example.hairbookfront.domain.repository.ApiRepository
import com.example.hairbookfront.util.ResourceState
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class signUpBarberViewModel @Inject constructor(
    private val hairBookRepository: ApiRepository,
    private val dataStorePreferences: DataStorePreferences,
    private val moshi: Moshi
) : ViewModel() {

    private val _firstName = MutableStateFlow("")
    val firstName: StateFlow<String>
        get() = _firstName

    private val _firstNameError = MutableStateFlow(false)
    val firstNameError: StateFlow<Boolean>
        get() = _firstNameError

    private val _lastName = MutableStateFlow("")
    val lastName: StateFlow<String>
        get() = _lastName

    private val _lastNameError = MutableStateFlow(false)
    val lastNameError: StateFlow<Boolean>
        get() = _lastNameError


    private val _yearsOfExperience = MutableStateFlow("")
    val yearsOfExperience: StateFlow<String>
        get() = _yearsOfExperience

    // Additional StateFlow for indicating whether the years of experience input is valid
    private val _yearsOfExperienceError = MutableStateFlow(false)
    val yearsOfExperienceError: StateFlow<Boolean>
        get() = _yearsOfExperienceError


    private val _email = MutableStateFlow("customer@customer.com")
    val email: StateFlow<String>
        get() = _email
    private val _password = MutableStateFlow("customer_password")
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

    //#
    // functions
//#
    fun firstNameChanged(firstName: String) {
        _firstName.value = firstName
    }

    fun lastNameChanged(lastName: String) {
        _lastName.value = lastName
    }

    fun yearsOfExperienceChanged(yearsOfExperience: String) {
        _yearsOfExperience.value = yearsOfExperience
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

    fun isFirstNameValid(): Boolean {
        val nameRegex = "^[a-zA-Z]+\$"
        val pattern = Pattern.compile(nameRegex)
        val matcher = pattern.matcher(_firstName.value)
        return matcher.matches()
    }

    fun isLastNameValid(): Boolean {
        val nameRegex = "^[a-zA-Z]+\$"
        val pattern = Pattern.compile(nameRegex)
        val matcher = pattern.matcher(_lastName.value)
        return matcher.matches()
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


    private fun isValidYearsOfExperience(): Boolean {
        try {
            val yearsOfExp = _yearsOfExperience.value.toInt()
            return yearsOfExp in 0..120
        } catch (e: NumberFormatException) {
            return false
        }
    }

    private fun sendMessage(message: String) {
        viewModelScope.launch {
            _toastMessage.emit(message)
        }
    }


    suspend fun signUpBarber() {
        if (isFirstNameValid())
            _firstNameError.value = false
        else {
            sendMessage("Invalid First Name")
            _firstNameError.value = true
        }

        if (isLastNameValid())
            _lastNameError.value = false
        else {
            sendMessage("Invalid Last Name")
            _lastNameError.value = true
        }
        if (isValidYearsOfExperience())
            _yearsOfExperienceError.value = false
        else {
            sendMessage("Invalid Years Of Experience")
            _yearsOfExperienceError.value = true
        }
        if (isValidEmail())
            _emailError.value = false
        else {
            sendMessage("Invalid Email")
            _emailError.value = true
        }
        if (isValidPassword())
            _passwordError.value = false
        else {
            sendMessage("Invalid Password")
            _passwordError.value = true
        }
    }
}

