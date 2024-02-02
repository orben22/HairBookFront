package com.example.hairbookfront.ui.shared.myBookings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
    viewModel: MyBookingsViewModel = hiltViewModel(),
    navController: NavController? = null,
) {
    val screen by viewModel.screen.collectAsStateWithLifecycle()
    val expanded by viewModel.isExpanded.collectAsStateWithLifecycle()
    val role by viewModel.role.collectAsState(initial = "")
    val bookingsList by viewModel.bookings.collectAsStateWithLifecycle()
    val servicesList by viewModel.services.collectAsStateWithLifecycle()
    LaunchedEffect(screen) {
        if (screen != "") {
            Timber.d("navigating....$screen")
            navController?.navigate(screen)
        }
    }
    Scaffold(
        topBar = {
            TopAppBarComponent(
                text = "My Bookings",
                onDismissRequest = viewModel::dismissMenu,
                expanded = expanded,
                expandFunction = viewModel::expandedFun,
                onClickMenus = listOf(
                    viewModel::profileClicked,
                    viewModel::signOut
                )
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
                    services = servicesList,
                    numberOfOptions = 1,
                    optionsIcons = listOf(Icons.Filled.Delete),
                    optionFunctions = listOf { viewModel.deleteBookings(it) }
                )
            }
            if (role == Constants.CustomerRole) {
                BookingsList(
                    isCustomer = true,
                    bookings = bookingsList,
                    services = servicesList,
                    optionFunctions = listOf({ viewModel.editBookings(it) },
                        { viewModel.deleteBookings(it) })
                )
            }
        }
    }
}