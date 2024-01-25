package com.example.hairbookfront.ui.auth.signUpBarber

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Build
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
import com.example.hairbookfront.ui.common.AppTextField
import com.example.hairbookfront.ui.common.ClickableText
import com.example.hairbookfront.ui.common.CustomButton
import com.example.hairbookfront.ui.common.TextFieldPassword
import com.example.hairbookfront.ui.common.TopAppBarHairBook
import com.example.hairbookfront.ui.navgraph.Routes
import kotlinx.coroutines.launch

@Composable
fun SignUpBarberScreen(
    signUpBarberViewModel: SignUpBarberViewModel = hiltViewModel(),
    navController: NavHostController? = null
) {
    val context = LocalContext.current
    val firstName by signUpBarberViewModel.firstName.collectAsStateWithLifecycle()
    val lastName by signUpBarberViewModel.lastName.collectAsStateWithLifecycle()
    val firstNameError by signUpBarberViewModel.firstNameError.collectAsStateWithLifecycle()
    val lastNameError by signUpBarberViewModel.lastNameError.collectAsStateWithLifecycle()
    val email by signUpBarberViewModel.email.collectAsStateWithLifecycle()
    val emailError by signUpBarberViewModel.emailError.collectAsStateWithLifecycle()
    val yearsOfExperience by signUpBarberViewModel.yearsOfExperience.collectAsStateWithLifecycle()
    val yearsOfExperienceError by
    signUpBarberViewModel.yearsOfExperienceError.collectAsStateWithLifecycle()
    val password by signUpBarberViewModel.password.collectAsStateWithLifecycle()
    val passwordError by signUpBarberViewModel.passwordError.collectAsStateWithLifecycle()
    val showOrHidePassword by signUpBarberViewModel.showOrHidePassword.collectAsStateWithLifecycle()
    val homeScreen by signUpBarberViewModel.homeScreen.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        signUpBarberViewModel
            .toastMessage
            .collect { message ->
                Toast.makeText(
                    context,
                    message,
                    Toast.LENGTH_SHORT,
                ).show()
            }
    }
    if (homeScreen != "") {
        navController?.navigate(homeScreen)
    }
    Surface(color = MaterialTheme.colorScheme.surface) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBarHairBook(text = "Barber Sign Up")
            AppTextField(
                value = firstName,
                placeholderText = "First Name",
                icon = Icons.Outlined.AccountCircle,
                onValueChange = { signUpBarberViewModel.firstNameChanged(it) },
                isError = firstNameError,
                keyboardOptions = KeyboardOptions.Default.copy(
                    capitalization = KeyboardCapitalization.Words,
                    keyboardType = KeyboardType.Text
                )
            )
            AppTextField(
                value = lastName,
                placeholderText = "Last Name",
                icon = Icons.Outlined.AccountCircle,
                onValueChange = { signUpBarberViewModel.lastNameChanged(it) },
                isError = lastNameError,
                keyboardOptions = KeyboardOptions.Default.copy(
                    capitalization = KeyboardCapitalization.Words,
                    keyboardType = KeyboardType.Text
                )
            )
            AppTextField(
                value = yearsOfExperience,
                placeholderText = "Years of Experience",
                icon = Icons.Outlined.Build,
                onValueChange = { signUpBarberViewModel.yearsOfExperienceChanged(it) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                isError = yearsOfExperienceError
            )
            AppTextField(
                value = email,
                placeholderText = "Email",
                icon = Icons.Outlined.Email,
                onValueChange = { signUpBarberViewModel.emailChanged(it) },
                isError = emailError
            )
            TextFieldPassword(
                password = password,
                onValueChange = { signUpBarberViewModel.passwordChanged(it) },
                isError = passwordError,
                onIconClicked = { signUpBarberViewModel.showOrHidePassword() },
                passwordVisibility = showOrHidePassword
            )
            CustomButton(
                text = "Sign Up",
                onClick = {
                    signUpBarberViewModel.viewModelScope.launch {
                        signUpBarberViewModel.signUpBarber()
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
