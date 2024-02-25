package com.example.hairbookfront.ui.shared.editOrCreateBooking

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.hairbookfront.HairBookApplication
import com.example.hairbookfront.ui.common.ButtonComponent
import com.example.hairbookfront.ui.common.DatePickerComponent
import com.example.hairbookfront.ui.common.ServiceCard
import com.example.hairbookfront.ui.common.TimeSlotsPicker
import com.example.hairbookfront.ui.common.TopAppBarComponent
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import timber.log.Timber


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditOrCreateBookingScreen(
    viewModel: EditOrCreateBookingViewModel = hiltViewModel(),
    navController: NavController = rememberNavController()
) {
    val context = LocalContext.current
    val selectedService by viewModel.selectedService.collectAsStateWithLifecycle()
    val selectedDate by viewModel.selectedDate.collectAsState()
    val selectedTimeSlot by viewModel.selectedTimeSlot.collectAsState()
    val calendarState = rememberSheetState()
    val screen by viewModel.screen.collectAsStateWithLifecycle()
    val shop by viewModel.shop.collectAsState()
    val services by viewModel.services.collectAsStateWithLifecycle()
    val availability by viewModel.availableBookingByDay.collectAsStateWithLifecycle()
    val mode by viewModel.mode.collectAsStateWithLifecycle()
    val lastScreen by viewModel.lastScreen.collectAsStateWithLifecycle()
    val myApp = context.applicationContext as HairBookApplication
    val randomBackground = remember { myApp.getRandomBackground() }
    val drawable = context.resources.getDrawable(randomBackground, null)
    val bitmap = drawable.toBitmap()
    val imageBitmap = bitmap.asImageBitmap()
    LaunchedEffect(screen) {
        if (screen != "") {
            viewModel.clearScreen()
            navController.popBackStack()
        }
    }
    LaunchedEffect(lastScreen) {
        if (lastScreen) {
            navController.popBackStack()
        }
    }

    LaunchedEffect(Unit) {
        viewModel
            .toastMessage
            .collect { message ->
                Toast.makeText(
                    context,
                    message,
                    Toast.LENGTH_SHORT,
                ).show()
            }
    }
    LaunchedEffect(selectedDate) {
        if (selectedDate != "") {
            viewModel.getAvailableBookingByDay()
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
                text = "$mode Booking",
                dropDownMenu = false
            )
        },
    ) { innerPadding ->
        Image(
            bitmap = imageBitmap,
            contentDescription = null,
            modifier = Modifier.fillMaxSize().alpha(0.4f),
            contentScale = ContentScale.Crop // This will make the image scale to fill the entire screen
        )
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(text = "Select a service")
            }
            if (shop != null && services.isNotEmpty()) {
                items(services.size) { serviceIndex ->
                    ServiceCard(
                        services[serviceIndex],
                        services[serviceIndex].serviceId == selectedService?.serviceId,
                        onServiceClick = { clickedService ->
                            viewModel.onServiceSelected(clickedService)
                        }
                    )
                }
            }

            item {
                Button(onClick = { calendarState.show() }) {
                    Icon(Icons.Default.DateRange, contentDescription = "Calendar Icon")
                    Text(text = "Pick Select Date")
                }
                if (selectedDate != "") {
                    Text(text = "Selected date: $selectedDate")
                }
            }
            item {
                if (selectedDate != "") {
                    if (availability.isNotEmpty()) {
                        TimeSlotsPicker(
                            selectedTimeSlot, viewModel::onTimeSlotSelected,
                            timeSlots = viewModel.getWorkingHours(),
                            availability = availability
                        )
                    }

                }
            }
            item {
                ButtonComponent(
                    text = "Confirm Booking",
                    onClick = viewModel::bookHaircutIfPossible,
                    icon = null
                )
            }
        }
        DatePickerComponent(getDisabledDates = viewModel::getDisabledDates,
            calendarState = calendarState,
            onDateSelected = { viewModel.onDateSelected(it) })
    }
}
