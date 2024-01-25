package com.example.hairbookfront.ui.customer.customerDetails

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.hairbookfront.ui.common.AppDialog
import com.example.hairbookfront.ui.common.CustomButton
import com.example.hairbookfront.ui.common.TopAppBarHairBook
import com.example.hairbookfront.ui.common.BookingCard
import com.example.hairbookfront.ui.common.BottomAppBarHairBook

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
    Scaffold(
        topBar = {
            TopAppBarHairBook("Customer Details")
        },
        bottomBar = {
            BottomAppBarHairBook()
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
                if (booking != null) {
                    BookingCard(true, booking!!, numberOfOptions = 2, optionFunctions = listOf(
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
                    AppDialog(
                        dialogTitle = "Delete confirmation",
                        dialogText = "Are you sure you want to delete this booking?",
                        confirmFunctions = listOf(customerDetailsViewModel::deleteBooking)
                    ) {
                    }
                }
                Column(modifier = Modifier.weight(1f)) {

                    // Button: Show My Reviews
                    CustomButton(
                        text = "Show My Reviews",
                        onClick = { /*TODO*/ },
                        icon = Icons.Filled.Star
                    )

                    // Button: Show Booking History
                    CustomButton(
                        text = "Show Booking History",
                        onClick = { /*TODO*/ },
                        icon = null
                    )
                }

            }
        }
    )
}
