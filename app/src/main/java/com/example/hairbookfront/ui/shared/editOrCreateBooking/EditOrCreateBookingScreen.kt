package com.example.hairbookfront.ui.shared.editOrCreateBooking

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.hairbookfront.ui.common.BottomAppBarComponent
import com.example.hairbookfront.ui.common.TopAppBarComponent


@Composable
fun EditOrCreateBookingScreen(
    viewModel: EditOrCreateBookingViewModel = hiltViewModel(),
    navController: NavController? = null,
) {

    Scaffold(
        topBar = {
            TopAppBarComponent(text = "Book hair cut")
        },
        bottomBar = {
            BottomAppBarComponent()
        },
        content = { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {

            }
        })
}