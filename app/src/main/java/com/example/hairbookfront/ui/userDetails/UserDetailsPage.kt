package com.example.hairbookfront.ui.userDetails

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hairbookfront.theme.HairBookFrontTheme
import com.example.hairbookfront.ui.common.CustomButton
import com.example.hairbookfront.ui.common.TopAppBarHairBook
import com.example.hairbookfront.ui.common.BookingCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailsScreen() {
    Scaffold(
        topBar = {
            TopAppBarHairBook("User Details")
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

                    text = "Full Name: Or Ben Ami ",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .align(Alignment.Start),
                    text = "Email: or123@gmail.com",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary

                )

                Text(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .align(Alignment.Start),
                    text = "Age: 26",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.height(50.dp))

                // Card: Upcoming Booking
                BookingCard( true,bookingData)

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


@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun UserDetailsPagePreviewDark() {
    HairBookFrontTheme {
        UserDetailsScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun UserDetailsPagePreviewLight() {
    HairBookFrontTheme {
        UserDetailsScreen()
    }
}
