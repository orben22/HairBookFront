package com.example.hairbookfront.ui.shared.viewShop

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.hairbookfront.theme.HairBookFrontTheme
import com.example.hairbookfront.ui.common.BottomAppBarComponent
import com.example.hairbookfront.ui.common.RatingComponent
import com.example.hairbookfront.ui.common.ReviewsList
import com.example.hairbookfront.ui.common.TopAppBarComponent
import com.example.hairbookfront.util.Constants
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ViewShopScreen(
    viewShopViewModel: ViewShopViewModel = hiltViewModel(),
    navController: NavHostController? = null
) {
    val barberShop by viewShopViewModel.barberShop.collectAsStateWithLifecycle()
    val screen by viewShopViewModel.screen.collectAsStateWithLifecycle()
    val expanded by viewShopViewModel.isExpanded.collectAsStateWithLifecycle()
    val role by viewShopViewModel.role.collectAsState()
    val reviews by viewShopViewModel.reviews.collectAsStateWithLifecycle()
    LaunchedEffect(screen) {
        if (screen != "") {
            navController?.navigate(screen)
        }
    }
    Scaffold(
        topBar = {
            TopAppBarComponent(
                text = "View Shop",
                onDismissRequest = viewShopViewModel::dismissMenu,
                expanded = expanded,
                expandFunction = viewShopViewModel::expandedFun,
                onClickMenus = listOf(viewShopViewModel::signOut, {})
            )
        },
        bottomBar = {
            if (role == Constants.CustomerRole) {
                BottomAppBarComponent(
                    onClickFunctions = listOf(viewShopViewModel::writeReview),
                    onClickFloating = {
                        viewShopViewModel.onFloatingActionButtonClicked()
                    }, icons = listOf(Icons.Filled.Edit),
                    floatingIcon = Icons.Filled.Add,
                    numberOfIcons = 1,
                    textToIcon = listOf("Write Review")
                )
            } else {
                BottomAppBarComponent(
                    onClickFunctions = listOf(viewShopViewModel::viewHistory),
                    textToIcon = listOf("Booking History"),
                    numberOfIcons = 1,
                    icons = listOf(
                        Icons.Filled.List
                    ),
                )
            }
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
            Text(text = barberShop.barberShopName, style = MaterialTheme.typography.headlineLarge)
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
            Text(text = "Reviews", style = MaterialTheme.typography.headlineMedium)
            if (reviews!=null) {
                ReviewsList(
                    reviews = reviews,
                    editable = false
                )
            }
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

