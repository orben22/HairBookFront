package com.example.hairbookfront.ui.customer.customerDetails

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.hairbookfront.ui.common.DialogComponent
import com.example.hairbookfront.ui.common.TopAppBarComponent
import com.example.hairbookfront.ui.common.BookingCardComponent
import com.example.hairbookfront.ui.common.BottomAppBarComponent
import timber.log.Timber

@Composable
fun CustomerDetailsScreen(
    viewModel: CustomerDetailsViewModel = hiltViewModel(),
    navController: NavController = rememberNavController()
) {
    val age by viewModel.getAge()
        .collectAsState(initial = 0)
    val firstname by viewModel.getFirstName().collectAsState(initial = "")
    val lastname by viewModel.getLastName().collectAsState(initial = "")
    val email by viewModel.getEmail().collectAsState(initial = "")
    val phoneNumber by viewModel.getPhoneNumber().collectAsState(initial = "")
    val booking by viewModel.closestBooking.collectAsStateWithLifecycle()
    val showOrHideDeleteDialog by viewModel.showOrHideDeleteDialogState.collectAsStateWithLifecycle()
    val screen by viewModel.screen.collectAsStateWithLifecycle()
    val expanded by viewModel.isExpanded.collectAsStateWithLifecycle()
    val serviceDetails by viewModel.serviceDetails.collectAsStateWithLifecycle()
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
        viewModel.getClosestBooking()
    }
    Scaffold(
        topBar = {
            TopAppBarComponent(
                "Customer Details",
                onDismissRequest = viewModel::dismissMenu,
                expanded = expanded,
                suggestions = listOf("Sign Out"),
                expandFunction = viewModel::expandedFun,
                onClickMenus = listOf(viewModel::signOut),
                onClickBackArrow = viewModel::onBackClicked
            )
        },
        bottomBar = {
            BottomAppBarComponent(
                textToIcon = listOf("My Bookings", "My Reviews"),
                icons = listOf(Icons.Default.List, Icons.Default.Edit),
                onClickFunctions = listOf(viewModel::viewMyBookings,
                    viewModel::viewMyReviews)
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // User Information
                Text(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .align(Alignment.Start),

                    text = "Full Name: $firstname $lastname",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
                Text(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .align(Alignment.Start),
                    text = "Email: $email",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black

                )

                Text(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .align(Alignment.Start),
                    text = "Age: $age",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )

                Text(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .align(Alignment.Start),
                    text = "Phone Number: $phoneNumber",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(50.dp))

                // Card: Upcoming Booking
                if (booking != null && serviceDetails != null) {
                    Text(
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .align(Alignment.CenterHorizontally),
                        text = "Upcoming Booking:",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black
                    )
                    BookingCardComponent(true, booking!!,
                        serviceDetails!!, numberOfOptions = 2, optionFunctions = listOf(
                            { viewModel.editBookingClicked(booking!!) },
                            { viewModel.showOrHideDeleteDialog() }
                        ))
                } else {
                    Text(
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .align(Alignment.CenterHorizontally),
                        text = "No upcoming bookings",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black
                    )
                }
                if (showOrHideDeleteDialog) {
                    DialogComponent(
                        dialogTitle = "Delete confirmation",
                        dialogText = "Are you sure you want to delete this booking?",
                        confirmFunctions = listOf(
                            viewModel::deleteBooking,
                            viewModel::onDismissRequest
                        ),
                        onDismissRequest = viewModel::onDismissRequest
                    )
                }
            }
        }
    )
}
