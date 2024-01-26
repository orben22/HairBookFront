package com.example.hairbookfront.ui.shared.MyBookings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.hairbookfront.ui.common.BookingsList
import com.example.hairbookfront.ui.common.BottomAppBarComponent
import com.example.hairbookfront.ui.common.TopAppBarComponent
import com.example.hairbookfront.util.Constants
import timber.log.Timber


@Composable
fun MyBookingsScreen(
    myBookingsViewModel: MyBookingsViewModel = hiltViewModel(),
    navController: NavController? = null,
) {
    val screen by myBookingsViewModel.screen.collectAsStateWithLifecycle()
    val expanded by myBookingsViewModel.isExpanded.collectAsStateWithLifecycle()
    val role by myBookingsViewModel.role.collectAsState(initial = "")
    val bookingsList by myBookingsViewModel.bookings.collectAsStateWithLifecycle()

    LaunchedEffect(screen) {
        if (screen != "") {
            Timber.d("navigating....$screen")
            navController?.navigate(screen)
        }
    }
    Scaffold(
        topBar = {
            TopAppBarComponent(text = "My Bookings",
                onDismissRequest = myBookingsViewModel::dismissMenu,
                expanded = expanded,
                expandFunction = myBookingsViewModel::expandedFun,
                onClickMenus = listOf(myBookingsViewModel::signOut, {})
            )
        },
        bottomBar = {
            BottomAppBarComponent()
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            if (role == Constants.BarberRole) {
                BookingsList(
                    isCustomer = false,
                    bookings = bookingsList,
                    numberOfOptions = 1,
                    optionsIcons = listOf(Icons.Filled.Delete),
                    optionFunctions = listOf { myBookingsViewModel.deleteBookings(it) }
                )
            }
            if (role == Constants.CustomerRole) {
                BookingsList(
                    isCustomer = true,
                    bookings = bookingsList,
                    optionFunctions = listOf({ myBookingsViewModel.editBookings(it) },
                        { myBookingsViewModel.deleteBookings(it) }))
            }
        }
    }
}