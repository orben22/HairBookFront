package com.example.hairbookfront.ui.auth.signUpCustomer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hairbookfront.data.datastore.DataStorePreferences
import com.example.hairbookfront.domain.entities.CustomerDTO
import com.example.hairbookfront.domain.entities.CustomerSignUpRequest
import com.example.hairbookfront.domain.repository.ApiRepositoryAuth
import com.example.hairbookfront.util.ResourceState
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
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
class SignUpCustomerViewModel @Inject constructor(
    private val hairBookRepository: ApiRepositoryAuth,
    private val dataStorePreferences: DataStorePreferences,
    private val moshi: Moshi
) : ViewModel() {

    private val _firstName = MutableStateFlow("or")
    val firstName: StateFlow<String>
        get() = _firstName
    private val _lastName = MutableStateFlow("ben")
    val lastName: StateFlow<String>
        get() = _lastName
    private val _phoneNumber = MutableStateFlow("0505971580")
    val phoneNumber: StateFlow<String>
        get() = _phoneNumber
    private val _age = MutableStateFlow("45")
    val age: StateFlow<String>
        get() = _age

    private val _email = MutableStateFlow("or@customer.com")
    val email: StateFlow<String>
        get() = _email
    private val _password = MutableStateFlow("or_password")
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

    private val _firstNameError = MutableStateFlow(false)
    val firstNameError: StateFlow<Boolean>
        get() = _firstNameError

    private val _lastNameError = MutableStateFlow(false)
    val lastNameError: StateFlow<Boolean>
        get() = _lastNameError

    private val _ageError = MutableStateFlow(false)
    val ageError: StateFlow<Boolean>
        get() = _ageError

    private val _phoneNumberError = MutableStateFlow(false)
    val phoneNumberError: StateFlow<Boolean>
        get() = _phoneNumberError

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    fun firstNameChanged(firstName: String) {
        _firstName.value = firstName
    }

    fun lastNameChanged(lastName: String) {
        _lastName.value = lastName
    }

    fun ageChanged(age: String) {
        _age.value = age
    }

    fun phoneNumberChanged(phoneNumber: String) {
        _phoneNumber.value = phoneNumber
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

    fun isAgeValid(): Boolean {
        try {
            val ageInt = _age.value.toInt()
            return ageInt in 0..120
        } catch (e: NumberFormatException) {
            return false
        }
    }

    fun isPhoneNumberValid(): Boolean {
        val phoneNumberRegex = "^\\d{10}\$"
        val pattern = Pattern.compile(phoneNumberRegex)
        val matcher = pattern.matcher(_phoneNumber.value)
        return matcher.matches()
    }

    private fun sendMessage(message: String) {
        viewModelScope.launch {
            _toastMessage.emit(message)
        }
    }

    suspend fun signUpCustomer() {

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
        if (isAgeValid())
            _ageError.value = false
        else {
            sendMessage("Invalid Age")
            _ageError.value = true
        }
        if (isPhoneNumberValid())
            _phoneNumberError.value = false
        else {
            sendMessage("Invalid Phone Number")
            _phoneNumberError.value = true
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
        val user = CustomerSignUpRequest(
            email = email.value,
            password = password.value,
            role = "Customer",
            details = CustomerDTO(
                firstName = firstName.value,
                lastName = lastName.value,
                age = age.value.toFloat(),
                phoneNumber = phoneNumber.value
            )
        )
        Timber.d("user: $user")

        if (isFirstNameValid() && isLastNameValid() && isAgeValid() && isPhoneNumberValid() && isValidEmail() && isValidPassword()) {
            _firstNameError.value = false
            _lastNameError.value = false
            _ageError.value = false
            _phoneNumberError.value = false
            _emailError.value = false
            _passwordError.value = false
            viewModelScope.launch {
                hairBookRepository.signUpCustomer(user).collectLatest { response ->
                    when (response) {
                        is ResourceState.LOADING -> {
                            sendMessage("Loading")
                        }

                        is ResourceState.SUCCESS -> {
                            sendMessage("Success")
                        }

                        is ResourceState.ERROR -> {
                            sendMessage(response.error)
                        }
                    }
                }
            }
        }
    }
}


