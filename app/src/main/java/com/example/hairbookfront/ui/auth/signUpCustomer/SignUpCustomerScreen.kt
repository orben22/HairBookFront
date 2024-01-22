package com.example.hairbookfront.ui.auth.signUpCustomer

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.example.hairbookfront.ui.common.SubmitButton
import com.example.hairbookfront.ui.common.AppTextField
import com.example.hairbookfront.ui.common.TextFieldPassword
import com.example.hairbookfront.ui.common.TopAppBarHairBook
import com.example.hairbookfront.theme.HairBookFrontTheme
import com.example.hairbookfront.ui.common.CustomButton
import kotlinx.coroutines.launch


@Composable
fun SignUpCustomerScreen(
    signUpCustomerViewModel: SignUpCustomerViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val firstName = signUpCustomerViewModel.firstName.collectAsStateWithLifecycle()
    val lastName = signUpCustomerViewModel.lastName.collectAsStateWithLifecycle()
    val age = signUpCustomerViewModel.age.collectAsStateWithLifecycle()
    val phoneNumber = signUpCustomerViewModel.phoneNumber.collectAsStateWithLifecycle()
    val email = signUpCustomerViewModel.email.collectAsStateWithLifecycle()
    val password = signUpCustomerViewModel.password.collectAsStateWithLifecycle()
    val emailError = signUpCustomerViewModel.emailError.collectAsStateWithLifecycle()
    val passwordError = signUpCustomerViewModel.passwordError.collectAsStateWithLifecycle()
    val showOrHidePassword = signUpCustomerViewModel.showOrHidePassword.collectAsStateWithLifecycle()
    Surface(color = MaterialTheme.colorScheme.surface) {

        // Compose UI components
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBarHairBook(text = "User Sign Up")
            AppTextField(
                value = firstName.value,
                placeholderText = "First Name",
                icon = Icons.Outlined.AccountCircle,
                onValueChange = { signUpCustomerViewModel.firstNameChanged(it)}
            )
            AppTextField(
                value = lastName.value,
                placeholderText = "Last Name",
                icon = Icons.Outlined.AccountCircle,
                onValueChange = { signUpCustomerViewModel.lastNameChanged(it) })
            AppTextField(
                value = age.value,
                placeholderText = "Age",
                icon = null,
                onValueChange = { signUpCustomerViewModel.ageChanged(it) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number))
            AppTextField(
                value = phoneNumber.value,
                placeholderText = "Phone Number",
                icon = Icons.Outlined.Call,
                onValueChange = { signUpCustomerViewModel.phoneNumberChanged(it) },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
            AppTextField(
                value = email.value,
                placeholderText = "Email",
                icon = Icons.Outlined.Email,
                onValueChange = { signUpCustomerViewModel.emailChanged(it)},
                isError = emailError.value
            )
            TextFieldPassword(
                password = password.value,
                onValueChange = { signUpCustomerViewModel.passwordChanged(it) },
                isError = passwordError.value,
                onIconClicked = { signUpCustomerViewModel.showOrHidePassword() },
                passwordVisibility = showOrHidePassword.value
            )
            CustomButton(
                text = "Sign Up",
                onClick = {
                    signUpCustomerViewModel.viewModelScope.launch {
                        signUpCustomerViewModel.signUp()
                    }
                },
                icon = null
            )
            Text(text = "Already have an account ? Sign In")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SignUpCustomerScreenPreviewDark() {
    HairBookFrontTheme {
        SignUpCustomerScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun SignUpCustomerScreenPreviewLight() {
    HairBookFrontTheme {
        SignUpCustomerScreen()
    }
}