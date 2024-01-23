package com.example.hairbookfront.ui.barber.barberDetails


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.hairbookfront.R
import com.example.hairbookfront.ui.Dimens.distanceFromLeft
import com.example.hairbookfront.ui.Dimens.distanceFromBottom
import com.example.hairbookfront.ui.common.BarberShopList
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarberDetailsScreen(
    barberDetailsViewModel: BarberDetailsViewModel = hiltViewModel(),
    navController: NavHostController? = null
) {

    val yearsOfExperience by barberDetailsViewModel.getYearsOfExperience().collectAsState(initial = 0)
    val firstname by barberDetailsViewModel.getFirstName().collectAsState(initial = "")
    val lastname by barberDetailsViewModel.getLastName().collectAsState(initial = "")
    val email by barberDetailsViewModel.getEmail().collectAsState(initial = "")
    val myShops by barberDetailsViewModel.myshops.collectAsStateWithLifecycle()
    Timber.d("experience: $yearsOfExperience")
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Barber Details")
                },
                navigationIcon = {
                    IconButton(onClick = { /* Handle navigation back */ }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                  IconButton(onClick = { /*TODO*/ }) {
                      Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null)
                  }
                }
            )
        },
        bottomBar = {
            Button(
                onClick = {
                    // Handle "Show Booking History" button click (Redirect to barber booking history)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp, start = distanceFromLeft, end = distanceFromLeft)
                    .height(IntrinsicSize.Min)
            ) {
                Text(text = stringResource(R.string.booking_history))
            }
        },
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
                    text = "Experience: $yearsOfExperience years.",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = "My barbershops:",
                    style = MaterialTheme.typography.bodyMedium
                )
                BarberShopList(barberShops = myShops, true)
            }
        }
    )
}