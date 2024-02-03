package com.example.hairbookfront.ui.auth.signUpBarber

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hairbookfront.data.datastore.DataStorePreferences
import com.example.hairbookfront.domain.entities.BarberDTO
import com.example.hairbookfront.domain.entities.BarberSignUpRequest
import com.example.hairbookfront.domain.entities.User
import com.example.hairbookfront.domain.repository.ApiRepositoryAuth
import com.example.hairbookfront.ui.navgraph.Routes
import com.example.hairbookfront.util.ResourceState
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


/**
 * ViewModel for the SignUpBarber screen.
 *
 * @property hairBookRepositoryAuth The repository for authentication related operations.
 * @property dataStorePreferences The datastore for storing preferences.
 */
@HiltViewModel
class SignUpBarberViewModel @Inject constructor(
    private val hairBookRepositoryAuth: ApiRepositoryAuth,
    private val dataStorePreferences: DataStorePreferences,
) : ViewModel() {

    private val _userDetails: MutableStateFlow<User?> =
        MutableStateFlow(null)

    private val _homeScreen = MutableStateFlow("")
    val homeScreen: StateFlow<String>
        get() = _homeScreen
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

    // functions
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

    private fun isFirstNameValid(): Boolean {
        val nameRegex = "^[a-zA-Z]+\$"
        val pattern = Pattern.compile(nameRegex)
        val matcher = pattern.matcher(_firstName.value)
        return matcher.matches()
    }

    private fun isLastNameValid(): Boolean {
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
        return try {
            val yearsOfExp = _yearsOfExperience.value.toInt()
            yearsOfExp in 0..120
        } catch (e: NumberFormatException) {
            false
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
            sendMessage("First name cannot be empty")
            _firstNameError.value = true
        }

        if (isLastNameValid())
            _lastNameError.value = false
        else {
            sendMessage("Last name cannot be empty")
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
            sendMessage("Invalid Password. Please enter at least 8 characters")
            _passwordError.value = true
        }
        if (isFirstNameValid() && isLastNameValid() && isValidYearsOfExperience() && isValidEmail() && isValidPassword()) {
            _firstNameError.value = false
            _lastNameError.value = false
            _yearsOfExperienceError.value = false
            _emailError.value = false
            _passwordError.value = false
            viewModelScope.launch {
                hairBookRepositoryAuth.signUpBarber(
                    BarberSignUpRequest(
                        email = email.value,
                        password = password.value,
                        role = "Barber",
                        details = BarberDTO(
                            firstName = firstName.value,
                            lastName = lastName.value,
                            yearsOfExperience = yearsOfExperience.value.toInt()
                        )
                    )
                ).collectLatest { response ->
                    when (response) {
                        is ResourceState.LOADING -> {
                            sendMessage("Loading")
                        }

                        is ResourceState.SUCCESS -> {
                            handleSignUpSuccess(response.data)
                        }

                        is ResourceState.ERROR -> {
                            sendMessage(response.error)
                        }
                    }
                }
            }
        }
    }

    private suspend fun handleSignUpSuccess(data: User) {
        _userDetails.emit(data)
        storeUserDetails()
        Timber.d("handleSignUpSuccess: $data")
        hairBookRepositoryAuth.getDetailsBarber(data.accessToken!!).collect {
            when (it) {
                is ResourceState.SUCCESS -> {
                    dataStorePreferences.storeBarberDetails(it.data)
                    sendMessage("Welcome ${it.data.firstName}")
                    _homeScreen.value = Routes.EditOrCreateBarberShopScreen.route
                }

                is ResourceState.ERROR -> sendMessage(it.error)
                else -> {}
            }
        }
    }

    private suspend fun storeUserDetails() {
        dataStorePreferences.storeUserDetails(_userDetails.value!!)
    }
}


