package com.example.hairbookfront.ui.auth.signUpBarber

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.hairbookfront.ui.common.SubmitButton
import com.example.hairbookfront.ui.common.AppTextField
import com.example.hairbookfront.theme.HairBookFrontTheme
import com.example.hairbookfront.ui.auth.welcome.WelcomeViewModel
import com.example.hairbookfront.ui.common.TextFieldPassword
import com.example.hairbookfront.ui.common.TopAppBarHairBook

@Composable
fun SignUpBarberScreen(
    signUpBarberViewModel: signUpBarberViewModel = hiltViewModel(),
    navController: NavHostController?
) {
    val firstName = signUpBarberViewModel.firstName.collectAsStateWithLifecycle()
    val lastName = signUpBarberViewModel.lastName.collectAsStateWithLifecycle()
    val email = signUpBarberViewModel.email.collectAsStateWithLifecycle()
    val emailError = signUpBarberViewModel.emailError.collectAsStateWithLifecycle()
    val years_of_experience = signUpBarberViewModel.yearsOfExperience.collectAsStateWithLifecycle()
    val years_of_experience_error =
        signUpBarberViewModel.yearsOfExperienceError.collectAsStateWithLifecycle()
    val password = signUpBarberViewModel.password.collectAsStateWithLifecycle()
    val passwordError = signUpBarberViewModel.passwordError.collectAsStateWithLifecycle()

    Surface(color = MaterialTheme.colorScheme.surface) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBarHairBook(text = "Barber Sign Up")
            AppTextField(
                value = firstName.value,
                placeholderText = "First Name",
                icon = Icons.Outlined.AccountCircle,
                onValueChange = { signUpBarberViewModel.firstNameChanged(it) })
            AppTextField(
                value = lastName.value,
                placeholderText = "Last Name",
                icon = Icons.Outlined.AccountCircle,
                onValueChange = { signUpBarberViewModel.lastNameChanged(it)})
            AppTextField(
                value = years_of_experience.value,
                placeholderText = "Years of Experience",
                icon = Icons.Outlined.Build,
                onValueChange = { signUpBarberViewModel.yearsOfExperienceChanged(it) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                )
            )
            AppTextField(
                value = email.value,
                placeholderText = "Email",
                icon = Icons.Outlined.Email,
                onValueChange = { signUpBarberViewModel.emailChanged(it) })
            TextFieldPassword(
                password = password.value,
                onValueChange = { },
                isError = false,
                onIconClicked = { },
                passwordVisibility = false
            )
            SubmitButton()
            Text(text = "Already have an account ? Sign In")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SignUpBarberScreenPreviewDark() {
    HairBookFrontTheme {
        SignUpBarberScreen(navController = null)
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun SignUpBarberScreenPreviewLight() {
    HairBookFrontTheme {
        SignUpBarberScreen(navController = null)
    }
}
//