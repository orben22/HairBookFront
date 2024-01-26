package com.example.hairbookfront.ui.customer.customerDetails

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
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
import com.example.hairbookfront.ui.common.DialogComponent
import com.example.hairbookfront.ui.common.ButtonComponent
import com.example.hairbookfront.ui.common.TopAppBarComponent
import com.example.hairbookfront.ui.common.BookingCardComponent
import com.example.hairbookfront.ui.common.BottomAppBarComponent
import timber.log.Timber

@Composable
fun CustomerDetailsScreen(
    customerDetailsViewModel: CustomerDetailsViewModel = hiltViewModel(),
    navController: NavController? = null,
) {
    val age by customerDetailsViewModel.getAge()
        .collectAsState(initial = 0)
    val firstname by customerDetailsViewModel.getFirstName().collectAsState(initial = "")
    val lastname by customerDetailsViewModel.getLastName().collectAsState(initial = "")
    val email by customerDetailsViewModel.getEmail().collectAsState(initial = "")
    val phoneNumber by customerDetailsViewModel.getPhoneNumber().collectAsState(initial = "")
    val booking by customerDetailsViewModel.closestBooking.collectAsState()
    val showOrHideDeleteDialog by customerDetailsViewModel.showOrHideDeleteDialogState.collectAsState()
    val screen by customerDetailsViewModel.screen.collectAsStateWithLifecycle()
    val expanded by customerDetailsViewModel.isExpanded.collectAsStateWithLifecycle()
    val serviceDetails by customerDetailsViewModel.serviceDetails.collectAsStateWithLifecycle()
    LaunchedEffect(screen) {
        if (screen != "") {
            Timber.d("navigating....")
            navController?.navigate(screen)
        }
    }
    Scaffold(
        topBar = {
            TopAppBarComponent(
                "Customer Details",
                onDismissRequest = customerDetailsViewModel::dismissMenu,
                expanded = expanded,
                expandFunction = customerDetailsViewModel::expandedFun,
                onClickMenus = listOf(customerDetailsViewModel::signOut, {})
            )
        },
        bottomBar = {
            BottomAppBarComponent()
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // User Information
                Spacer(modifier = Modifier.height(100.dp))
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
                    BookingCardComponent(true, booking!!,
                        serviceDetails!!, numberOfOptions = 2, optionFunctions = listOf(
                            { },
                            { customerDetailsViewModel.showOrHideDeleteDialog() }
                        ))
                } else {
                    Text(
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .align(Alignment.Start),
                        text = "No upcoming bookings",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black
                    )
                }
                if (showOrHideDeleteDialog) {
                    DialogComponent(
                        dialogTitle = "Delete confirmation",
                        dialogText = "Are you sure you want to delete this booking?",
                        confirmFunctions = listOf(customerDetailsViewModel::deleteBooking)
                    ) {
                    }
                }
                Column(modifier = Modifier.weight(1f)) {

                    // Button: Show My Reviews
                    ButtonComponent(
                        text = "Show My Reviews",
                        onClick = { /*TODO*/ },
                        icon = Icons.Filled.Star
                    )

                    // Button: Show Booking History
                    ButtonComponent(
                        text = "Show Booking History",
                        onClick = { /*TODO*/ },
                        icon = null
                    )
                }

            }
        }
    )
}
