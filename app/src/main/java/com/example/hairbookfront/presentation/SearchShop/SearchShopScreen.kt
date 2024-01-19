package com.example.hairbookfront.presentation.SearchShop

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Search
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
import com.example.hairbookfront.presentation.common.AppTextField
import com.example.hairbookfront.presentation.common.SubmitButton
import com.example.hairbookfront.presentation.common.TextFieldSignUpPassword
import com.example.hairbookfront.theme.HairBookFrontTheme

@Composable
fun SearchShop() {
    Surface(color = MaterialTheme.colorScheme.surface) {


        val search = remember { mutableStateOf("") }
        val lastName = remember { mutableStateOf("") }
        val phoneNumber = remember { mutableStateOf("") }
        val email = remember { mutableStateOf("") }
        val years_of_experience = remember { mutableStateOf("") }
        val password = remember { mutableStateOf("") }
        var isValid by remember { mutableStateOf(false) }

        // Compose UI components
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppTextField(value =search.value , placeholderText ="Search BarberShop..." , icon = Icons.Outlined.Search , onValueChange = { search.value = it })
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun myReviewDark() {
    HairBookFrontTheme {


        SearchShop()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun myReview() {
    HairBookFrontTheme {
        SearchShop()
    }
}