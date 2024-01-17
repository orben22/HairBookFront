package com.example.hairbookfront.presentation.signUpBarber

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.hairbookfront.presentation.Dimens
import com.example.hairbookfront.presentation.common.SubmitButton
import com.example.hairbookfront.theme.HairBookFrontTheme

@Composable
fun SignUpBarber() {
    Surface(color = MaterialTheme.colorScheme.surface) {


        val firstName = remember { mutableStateOf("") }
        val lastName = remember { mutableStateOf("") }
        val phoneNumber = remember { mutableStateOf("") }
        val email = remember { mutableStateOf("") }
        val years_of_experience = remember { mutableStateOf("") }
        var isValid by remember { mutableStateOf(false) }

        // Compose UI components
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.mediumPadding1),
                value = firstName.value,
                onValueChange = { firstName.value = it },
                placeholder = { Text(text = "First Name") }
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.mediumPadding1),
                value = lastName.value,
                onValueChange = { lastName.value = it },
                placeholder = { Text(text = "Last Name") }
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.mediumPadding1),
                value = years_of_experience.value,
                onValueChange = { years_of_experience.value = it },
                placeholder = { Text(text = "Years of Experience") }
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.mediumPadding1),
                value = phoneNumber.value,
                onValueChange = { phoneNumber.value = it },
                placeholder = { Text(text = "Phone Number") },
                trailingIcon ={ Icon(
                    Icons.Outlined.Call,
                    contentDescription = null) }
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.mediumPadding1),
                value = email.value,
                onValueChange = {
                    email.value = it
                },
                placeholder = { Text(text = "Email") },
                trailingIcon ={ Icon(
                    Icons.Outlined.Email,
                    contentDescription = null) }
            )
            var password by remember { mutableStateOf("") }
            TextField(value = password,
                onValueChange = {password = it},
                placeholder = { Text("Password") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.mediumPadding1),
                trailingIcon ={ Icon(
                    Icons.Outlined.Close,
                    contentDescription = null) }
            )
            SubmitButton ()
        }
    }
}
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun myReviewDark() {
    HairBookFrontTheme {


        SignUpBarber()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun myReview() {
    HairBookFrontTheme {
        SignUpBarber()
    }
}