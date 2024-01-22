package com.example.hairbookfront.ui.auth.signUpBarber

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import com.example.hairbookfront.di.DataStorePreferences
import com.example.hairbookfront.domain.repository.ApiRepository
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    private val _lastName = MutableStateFlow("")
    val lastName: StateFlow<String>
        get() = _lastName

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
        validateYearsOfExperience(yearsOfExperience)
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


    // Function to validate the "Years of Experience" input
    private fun validateYearsOfExperience(yearsOfExperience: String) {
        _yearsOfExperienceError.value = !yearsOfExperience.isDigitsOnly()
    }


    suspend fun signUpBarber() {


    }
}