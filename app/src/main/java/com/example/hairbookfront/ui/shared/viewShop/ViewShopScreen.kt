package com.example.hairbookfront.ui.shared.viewShop

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.hairbookfront.R
import com.example.hairbookfront.domain.entities.BarberShop
import com.example.hairbookfront.domain.entities.Review
import com.example.hairbookfront.theme.HairBookFrontTheme
import com.example.hairbookfront.ui.Dimens
import com.example.hairbookfront.ui.common.BottomAppBarComponent
import com.example.hairbookfront.ui.common.RatingComponent
import com.example.hairbookfront.ui.common.ReviewsList
import com.example.hairbookfront.ui.common.TopAppBarComponent
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ViewShopScreen(
    navController: NavHostController? = null
) {
    val barberShop = BarberShop(
        barbershopId = "1",
        barbershopName = "barbershopName",
        barberName = "barberName",
        phoneNumber = "phoneNumber",
        workingDays = listOf(1.0f, 1.0f, 0f, 0f, 0f, 0f, 0f),
        sundayHours = listOf("10:00", "10:30", "11:00"),
        mondayHours = listOf("10:00", "10:30", "11:00", "11:30"),
        tuesdayHours = listOf(),
        wednesdayHours = listOf(),
        thursdayHours = listOf(),
        fridayHours = listOf(),
        saturdayHours = listOf(),
        totalRating = 4.5f,
        location = "location",
        description = "description",
    )
    Scaffold(
        topBar = {
            TopAppBarComponent(text = "View Shop")
        },
        bottomBar = {
            BottomAppBarComponent()
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = barberShop.barbershopName, style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Address: ${barberShop.location}",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = barberShop.description, style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Phone Number: ${barberShop.phoneNumber}",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(16.dp))

            RatingComponent(rating = barberShop.totalRating)
            Spacer(modifier = Modifier.height(16.dp))
            val daysOfWeek =
                listOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
            val hoursOfWeek = listOf(
                barberShop.sundayHours,
                barberShop.mondayHours,
                barberShop.tuesdayHours,
                barberShop.wednesdayHours,
                barberShop.thursdayHours,
                barberShop.fridayHours,
                barberShop.saturdayHours
            )
            for (i in barberShop.workingDays.indices) {
                WorkingHours(daysOfWeek[i], hoursOfWeek[i], barberShop.workingDays[i] == 1.0f)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text ="Reviews", style = MaterialTheme.typography.headlineMedium)
            ReviewsList(
                reviews = listOf(
                    Review(
                        reviewId = "1",
                        firstName = "firstName",
                        lastName = "lastName",
                        review = "review",
                        rating = 3.6f,
                        timestamp = "timestamp",
                        userId = "userId",
                        barbershopId = "barbershopId"
                    )
                ),
                editable = false
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WorkingHours(dayOfWeek: String, hours: List<String>?, isOpen: Boolean) {
    if (isOpen && !hours.isNullOrEmpty()) {
        val openTime = hours.first()
        var closeTime = hours.last()
        closeTime = if (closeTime.endsWith(":00")) {
            val parsedTime = LocalTime.parse(closeTime, DateTimeFormatter.ofPattern("HH:mm"))
            parsedTime.plusMinutes(30).format(DateTimeFormatter.ofPattern("HH:mm"))
        } else {
            val parsedTime = LocalTime.parse(closeTime, DateTimeFormatter.ofPattern("HH:mm"))
            parsedTime.plusHours(1).minusMinutes(30)
                .format(DateTimeFormatter.ofPattern("HH:mm"))
        }
        Text(
            text = "$dayOfWeek: $openTime - $closeTime",
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SignUpBarberScreenPreviewDark() {
    HairBookFrontTheme {
        ViewShopScreen()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun SignUpBarberScreenPreviewLight() {
    HairBookFrontTheme {
        ViewShopScreen()
    }
}

