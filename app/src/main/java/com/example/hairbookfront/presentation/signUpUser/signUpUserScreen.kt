package com.example.hairbookfront.presentation.signUpUser

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.Visibility
import com.example.hairbookfront.R
import com.example.hairbookfront.presentation.Dimens
import com.example.hairbookfront.presentation.Dimens.mediumPadding1
import com.example.hairbookfront.theme.HairBookFrontTheme

fun isValidText(text: String): Boolean {
    // Add your custom validation rules here
    return text.matches(Regex("/^([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,})\$/"))
}

@Composable
fun SignUpUser() {
    Surface(color = MaterialTheme.colorScheme.surface) {


        val firstName = remember { mutableStateOf("") }
        val lastName = remember { mutableStateOf("") }
        val phoneNumber = remember { mutableStateOf("") }
        val email = remember { mutableStateOf("") }
        val age = remember { mutableStateOf("") }
        var isValid by remember { mutableStateOf(false) }

        // Compose UI components
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TextField(
                modifier = Modifier.fillMaxWidth().padding(mediumPadding1),
                value = firstName.value,
                onValueChange = { firstName.value = it },
                placeholder = { Text(text = "First Name") }
            )
            TextField(
                modifier = Modifier.fillMaxWidth().padding(mediumPadding1),
                value = lastName.value,
                onValueChange = { lastName.value = it },
                placeholder = { Text(text = "Last Name") }
            )
            TextField(
                modifier = Modifier.fillMaxWidth().padding(mediumPadding1),
                value = phoneNumber.value,
                onValueChange = { phoneNumber.value = it },
                placeholder = { Text(text = "Phone Number") }
            )
            TextField(
                modifier = Modifier.fillMaxWidth().padding(mediumPadding1),
                value = email.value,
                onValueChange = {
                    email.value = it
                },
                placeholder = { Text(text = "Email") }
            )
            TextField(
                modifier = Modifier.fillMaxWidth().padding(mediumPadding1),
                value = age.value,
                onValueChange = { age.value = it },
                placeholder = { Text(text = "Age") }
            )
            var password by remember { mutableStateOf("")}
            TextField(value = password,
                onValueChange = {password = it},
                placeholder = { Text("Password") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
                    .padding(mediumPadding1))


//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(Dimens.smallPadding3),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Icon(
//            painter = painterResource(id = R.drawable.baseline_app_blocking_24),
//            contentDescription = "empty state",
//            modifier = Modifier
//                .size(Dimens.largePadding1)
//                .padding(Dimens.smallPadding2),
//            tint = Color.Gray
//        )
//        Text(
//            text = "No data found",
//            style = TextStyle(
//                fontSize = Dimens.fontLarge,
//                fontWeight = FontWeight.Normal
//            )
//        )
//    }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun myReviewDark() {
    HairBookFrontTheme {


        SignUpUser()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun myReview() {
    HairBookFrontTheme {
        SignUpUser()
    }
}