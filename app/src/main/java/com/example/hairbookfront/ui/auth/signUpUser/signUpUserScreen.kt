package com.example.hairbookfront.ui.auth.signUpUser

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
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
import androidx.compose.ui.tooling.preview.Preview
import com.example.hairbookfront.ui.common.SubmitButton
import com.example.hairbookfront.ui.common.AppTextField
import com.example.hairbookfront.ui.common.TextFieldPassword
import com.example.hairbookfront.ui.common.TopAppBarHairBook
import com.example.hairbookfront.theme.HairBookFrontTheme


@Composable
fun SignUpUserScreen() {
    Surface(color = MaterialTheme.colorScheme.surface) {
        val firstName = remember { mutableStateOf("") }
        val lastName = remember { mutableStateOf("") }
        val phoneNumber = remember { mutableStateOf("") }
        val email = remember { mutableStateOf("") }
        val age = remember { mutableStateOf("") }
        val password = remember { mutableStateOf("") }
        var isValid by remember { mutableStateOf(false) }

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
                onValueChange = { firstName.value = it })
            AppTextField(
                value = lastName.value,
                placeholderText = "Last Name",
                icon = Icons.Outlined.AccountCircle,
                onValueChange = { lastName.value = it })
            AppTextField(
                value = age.value,
                placeholderText = "Age",
                icon = null,
                onValueChange = { age.value = it })
            AppTextField(
                value = phoneNumber.value,
                placeholderText = "Phone Number",
                icon = Icons.Outlined.Call,
                onValueChange = { phoneNumber.value = it })
            AppTextField(
                value = email.value,
                placeholderText = "Email",
                icon = Icons.Outlined.Email,
                onValueChange = { email.value = it })
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
fun SignUpUserScreenPreviewDark() {
    HairBookFrontTheme {
        SignUpUserScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun SignUpUserScreenPreviewLight() {
    HairBookFrontTheme {
        SignUpUserScreen()
    }
}