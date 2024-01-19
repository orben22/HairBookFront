package com.example.hairbookfront.presentation.SearchShop

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.hairbookfront.presentation.common.AppTextField
import com.example.hairbookfront.presentation.common.BarberShopList
import com.example.hairbookfront.presentation.common.TopAppBarHairBook
import com.example.hairbookfront.theme.HairBookFrontTheme

@Composable
fun SearchShop() {
    Surface(color = MaterialTheme.colorScheme.surface) {


        val search = remember { mutableStateOf("") }

        // Compose UI components
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBarHairBook(text = "Search Shop")
            AppTextField(value =search.value , placeholderText ="Search BarberShop..." , icon = Icons.Outlined.Search , onValueChange = { search.value = it })
            BarberShopList()
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