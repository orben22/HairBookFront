package com.example.hairbookfront.ui.barber.barberDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hairbookfront.R
import com.example.hairbookfront.ui.Dimens.distanceFromLeft
import com.example.hairbookfront.ui.Dimens.distanceFromBottom
import com.example.hairbookfront.ui.common.BarberShopList
import com.example.hairbookfront.ui.common.TopAppBarHairBook

@Composable
fun BarberDetailsScreen(
    barberDetailsViewModel: BarberDetailsViewModel = hiltViewModel()
) {
    val firstname = barberDetailsViewModel.firstName.collectAsStateWithLifecycle()
    val lastname = barberDetailsViewModel.lastName.collectAsStateWithLifecycle()
    val experience = barberDetailsViewModel.exp.collectAsStateWithLifecycle()
    val email = barberDetailsViewModel.email.collectAsStateWithLifecycle()
    Scaffold(
        topBar = { TopAppBarHairBook("Barber Details") },
        bottomBar = {
            Button(
                onClick = {
                    // Handle "Show Booking History" button click (Redirect to barber booking history)
                },
                modifier = Modifier
                    .fillMaxWidth() // This will make the button take the full width of its parent
                    .padding(bottom = 20.dp, start = distanceFromLeft, end = distanceFromLeft)
                    .height(IntrinsicSize.Min)
            ) {
                Text(text = stringResource(R.string.booking_history))
            }
        }
        ,
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(
                        start = distanceFromLeft,
                        end = distanceFromLeft,
                        top = distanceFromBottom,
                        bottom = distanceFromBottom
                    )
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                // User Information (You can use your own user data here if available)
                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = "Full Name: $firstname $lastname",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = "Email: $email",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = "Experience: $experience years.",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = "My barbershops:",
                    style = MaterialTheme.typography.bodyMedium
                )
                BarberShopList(true)
            }
        }
    )
}