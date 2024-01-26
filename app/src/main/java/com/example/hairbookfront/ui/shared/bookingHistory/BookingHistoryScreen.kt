package com.example.hairbookfront.ui.shared.bookingHistory

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.hairbookfront.ui.common.BottomAppBarComponent
import com.example.hairbookfront.ui.common.TopAppBarComponent
import timber.log.Timber


@Composable
fun BookingHistoryScreen(
    bookingHistoryViewModel: BookingHistoryViewModel = hiltViewModel(),
    navController: NavController? = null,
) {
    val screen by bookingHistoryViewModel.screen.collectAsStateWithLifecycle()
    val expanded by bookingHistoryViewModel.isExpanded.collectAsStateWithLifecycle()

    LaunchedEffect(screen) {
        if (screen != "") {
            Timber.d("navigating....$screen")
            navController?.navigate(screen)
        }
    }
    Scaffold(
        topBar = {
            TopAppBarComponent(text = "Booking History",
                onDismissRequest = bookingHistoryViewModel::dismissMenu,
                expanded = expanded,
                expandFunction = bookingHistoryViewModel::expandedFun,
                onClickMenus = listOf(bookingHistoryViewModel::signOut,{}))
        },
        bottomBar = {
            BottomAppBarComponent()
        },
        content = { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {

            }
        })

}