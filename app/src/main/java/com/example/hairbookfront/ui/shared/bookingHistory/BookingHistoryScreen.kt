package com.example.hairbookfront.ui.shared.bookingHistory

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
fun BookingHistoryScreen(
    viewModel: BookingHistoryViewModel = hiltViewModel(),
    navController: NavController? = null,
) {

    Scaffold(
        topBar = {
            TopAppBarComponent(text = "Booking History")
        },
        bottomBar = {
            BottomAppBarComponent()
        },
        content = { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {

            }
        })

}