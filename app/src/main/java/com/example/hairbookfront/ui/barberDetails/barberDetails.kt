package com.example.hairbookfront.ui.barberDetails

import android.content.res.Configuration
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hairbookfront.R
import com.example.hairbookfront.ui.Dimens.distanceFromLeft
import com.example.hairbookfront.theme.HairBookFrontTheme
import com.example.hairbookfront.ui.Dimens.distanceFromBottom
import com.example.hairbookfront.ui.common.BarberShopList
import com.example.hairbookfront.ui.common.TopAppBarHairBook
@Composable
fun BarberDetailsScreen() {
    Scaffold(
        topBar = { TopAppBarHairBook("Barber Details") },
        bottomBar = {
            Button(
                onClick = {
                    // Handle "Show Booking History" button click
                },
                modifier = Modifier
                    .fillMaxWidth() // This will make the button take the full width of its parent
                    .padding(bottom = 20.dp, start = distanceFromLeft,end = distanceFromLeft)
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
                    .padding(start = distanceFromLeft,end = distanceFromLeft ,top = distanceFromBottom, bottom = distanceFromBottom)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                // User Information (You can use your own user data here if available)
                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = "Full Name: Amit Kabalo",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = "Email: amitkabalo12101996@gmail.com",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = "Experience: 10 years of masturbation",
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
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MyReviewDark() {
    HairBookFrontTheme {
        BarberDetailsScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun MyReview() {
    HairBookFrontTheme {
        BarberDetailsScreen()
    }
}