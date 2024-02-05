package com.example.hairbookfront.ui.auth.signUpCustomer

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.navigation.compose.rememberNavController
import com.example.hairbookfront.ui.common.TextFieldComponent
import com.example.hairbookfront.ui.common.TextFieldPasswordComponent
import com.example.hairbookfront.ui.common.TopAppBarComponent
import com.example.hairbookfront.ui.common.ClickableText
import com.example.hairbookfront.ui.common.ButtonComponent
import com.example.hairbookfront.ui.navgraph.Routes
import kotlinx.coroutines.launch


@Composable
fun SignUpCustomerScreen(
    viewModel: SignUpCustomerViewModel = hiltViewModel(),
    navController: NavHostController= rememberNavController()
) {
    val context = LocalContext.current
    val firstName by viewModel.firstName.collectAsStateWithLifecycle()
    val lastName by viewModel.lastName.collectAsStateWithLifecycle()
    val age by viewModel.age.collectAsStateWithLifecycle()
    val phoneNumber by viewModel.phoneNumber.collectAsStateWithLifecycle()
    val email by viewModel.email.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()
    val firstNameError by viewModel.firstNameError.collectAsStateWithLifecycle()
    val lastNameError by viewModel.lastNameError.collectAsStateWithLifecycle()
    val ageError by viewModel.ageError.collectAsStateWithLifecycle()
    val phoneNumberError by viewModel.phoneNumberError.collectAsStateWithLifecycle()
    val emailError by viewModel.emailError.collectAsStateWithLifecycle()
    val passwordError by viewModel.passwordError.collectAsStateWithLifecycle()
    val showOrHidePassword by viewModel.showOrHidePassword.collectAsStateWithLifecycle()
    val screen by viewModel.screen.collectAsStateWithLifecycle()
    val lastScreen by viewModel.lastScreen.collectAsStateWithLifecycle()

    LaunchedEffect(screen) {
        if (screen != "") {
            viewModel.clearScreen()
            navController.navigate(screen)
        }
    }
    LaunchedEffect(lastScreen) {
        if (lastScreen) {
            navController.popBackStack()
        }
    }
    LaunchedEffect(Unit) {
        viewModel
            .toastMessage
            .collect { message ->
                Toast.makeText(
                    context,
                    message,
                    Toast.LENGTH_SHORT,
                ).show()
            }
    }
    Scaffold(topBar = {
        TopAppBarComponent(text = "Customer Sign Up",
            onClickBackArrow = viewModel::onBackClicked,
            dropDownMenu = false)
    }) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextFieldComponent(
                value = firstName,
                placeholderText = "First Name",
                icon = Icons.Outlined.AccountCircle,
                onValueChange = { viewModel.firstNameChanged(it) },
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
                onValueChange = { viewModel.lastNameChanged(it) },
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
                onValueChange = { viewModel.ageChanged(it) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                isError = ageError
            )
            TextFieldComponent(
                value = phoneNumber,
                placeholderText = "Phone Number",
                icon = Icons.Outlined.Call,
                onValueChange = { viewModel.phoneNumberChanged(it) },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                isError = phoneNumberError
            )
            TextFieldComponent(
                value = email,
                placeholderText = "Email",
                icon = Icons.Outlined.Email,
                onValueChange = { viewModel.emailChanged(it) },
                isError = emailError
            )
            TextFieldPasswordComponent(
                password = password,
                onValueChange = { viewModel.passwordChanged(it) },
                isError = passwordError,
                onIconClicked = { viewModel.showOrHidePassword() },
                passwordVisibility = showOrHidePassword
            )
            ButtonComponent(
                text = "Sign Up",
                onClick = {
                    viewModel.viewModelScope.launch {
                        viewModel.signUpCustomer()
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
                    onClick = { navController.navigate(Routes.WelcomeScreen.route) },
                    color = Color.Cyan,
                    fontSize = 20
                )
            }
        }
    }
}

