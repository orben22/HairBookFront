package com.example.hairbookfront.ui.auth.signUpCustomer

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.hairbookfront.ui.common.TextFieldComponent
import com.example.hairbookfront.ui.common.TextFieldPasswordComponent
import com.example.hairbookfront.ui.common.TopAppBarComponent
import com.example.hairbookfront.ui.common.ClickableText
import com.example.hairbookfront.ui.common.ButtonComponent
import com.example.hairbookfront.ui.navgraph.Routes
import kotlinx.coroutines.launch


@Composable
fun SignUpCustomerScreen(
    signUpCustomerViewModel: SignUpCustomerViewModel = hiltViewModel(),
    navController: NavHostController?
) {
    val context = LocalContext.current
    val firstName by signUpCustomerViewModel.firstName.collectAsStateWithLifecycle()
    val lastName by signUpCustomerViewModel.lastName.collectAsStateWithLifecycle()
    val age by signUpCustomerViewModel.age.collectAsStateWithLifecycle()
    val phoneNumber by signUpCustomerViewModel.phoneNumber.collectAsStateWithLifecycle()
    val email by signUpCustomerViewModel.email.collectAsStateWithLifecycle()
    val password by signUpCustomerViewModel.password.collectAsStateWithLifecycle()
    val firstNameError by signUpCustomerViewModel.firstNameError.collectAsStateWithLifecycle()
    val lastNameError by signUpCustomerViewModel.lastNameError.collectAsStateWithLifecycle()
    val ageError by signUpCustomerViewModel.ageError.collectAsStateWithLifecycle()
    val phoneNumberError by signUpCustomerViewModel.phoneNumberError.collectAsStateWithLifecycle()
    val emailError by signUpCustomerViewModel.emailError.collectAsStateWithLifecycle()
    val passwordError by signUpCustomerViewModel.passwordError.collectAsStateWithLifecycle()
    val showOrHidePassword by signUpCustomerViewModel.showOrHidePassword.collectAsStateWithLifecycle()
    val homeScreen by signUpCustomerViewModel.homeScreen.collectAsStateWithLifecycle()

    LaunchedEffect(homeScreen) {
        if (homeScreen != "") {
            navController?.navigate(homeScreen)
        }
    }
    LaunchedEffect(Unit) {
        signUpCustomerViewModel
            .toastMessage
            .collect { message ->
                Toast.makeText(
                    context,
                    message,
                    Toast.LENGTH_SHORT,
                ).show()
            }
    }
    Surface(color = MaterialTheme.colorScheme.surface) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBarComponent(text = "Customer Sign Up")
            TextFieldComponent(
                value = firstName,
                placeholderText = "First Name",
                icon = Icons.Outlined.AccountCircle,
                onValueChange = { signUpCustomerViewModel.firstNameChanged(it) },
                isError = firstNameError,
                keyboardOptions = KeyboardOptions.Default.copy(
                    capitalization = KeyboardCapitalization.Words,
                    keyboardType = KeyboardType.Text
                )
            )
            TextFieldComponent(
                value = lastName,
                placeholderText = "Last Name",
                icon = Icons.Outlined.AccountCircle,
                onValueChange = { signUpCustomerViewModel.lastNameChanged(it) },
                isError = lastNameError,
                keyboardOptions = KeyboardOptions.Default.copy(
                    capitalization = KeyboardCapitalization.Words,
                    keyboardType = KeyboardType.Text
                )
            )
            TextFieldComponent(
                value = age,
                placeholderText = "Age",
                icon = null,
                onValueChange = { signUpCustomerViewModel.ageChanged(it) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                isError = ageError
            )
            TextFieldComponent(
                value = phoneNumber,
                placeholderText = "Phone Number",
                icon = Icons.Outlined.Call,
                onValueChange = { signUpCustomerViewModel.phoneNumberChanged(it) },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                isError = phoneNumberError
            )
            TextFieldComponent(
                value = email,
                placeholderText = "Email",
                icon = Icons.Outlined.Email,
                onValueChange = { signUpCustomerViewModel.emailChanged(it) },
                isError = emailError
            )
            TextFieldPasswordComponent(
                password = password,
                onValueChange = { signUpCustomerViewModel.passwordChanged(it) },
                isError = passwordError,
                onIconClicked = { signUpCustomerViewModel.showOrHidePassword() },
                passwordVisibility = showOrHidePassword
            )
            ButtonComponent(
                text = "Sign Up",
                onClick = {
                    signUpCustomerViewModel.viewModelScope.launch {
                        signUpCustomerViewModel.signUpCustomer()
                    }
                },
                icon = null
            )
            Row {
                Text(
                    text = "Already have an account?",
                    modifier = Modifier,
                    fontSize = 20.sp
                )
                ClickableText(
                    text = " Sign In",
                    onClick = { navController?.navigate(Routes.WelcomeScreen.route) },
                    color = Color.Cyan,
                    fontSize = 20
                )
            }
        }
    }
}

