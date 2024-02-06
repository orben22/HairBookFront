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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.hairbookfront.ui.common.BookingsList
import com.example.hairbookfront.ui.common.BottomAppBarComponent
import com.example.hairbookfront.ui.common.TopAppBarComponent
import com.example.hairbookfront.util.Constants
import timber.log.Timber


@Composable
fun MyBookingsScreen(
    viewModel: MyBookingsViewModel = hiltViewModel(),
    navController: NavController = rememberNavController()
) {
    val screen by viewModel.screen.collectAsStateWithLifecycle()
    val expanded by viewModel.isExpanded.collectAsStateWithLifecycle()
    val role by viewModel.role.collectAsState(initial = "")
    val bookingsList by viewModel.bookings.collectAsStateWithLifecycle()
    val servicesList by viewModel.services.collectAsStateWithLifecycle()
    val lastScreen by viewModel.lastScreen.collectAsStateWithLifecycle()
    LaunchedEffect(screen) {
        if (screen != "") {
            viewModel.clearScreen()
            navController.navigate(screen)
        }
    }
    LaunchedEffect(lastScreen) {
        if (lastScreen) {
            navController.popBackStack()
        }
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    LaunchedEffect(currentRoute) {
        viewModel.refreshData()
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
                    viewModel::signOut),
                onClickBackArrow = viewModel::onBackClicked
            )
        },
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