package com.example.hairbookfront.ui.shared.editOrCreateBooking

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.hairbookfront.ui.common.BottomAppBarComponent
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
    navController: NavController? = null,
) {
    val context = LocalContext.current
    val selectedService by viewModel.selectedService.collectAsStateWithLifecycle()
    val selectedDate by viewModel.selectedDate.collectAsState()
    val selectedTimeSlot by viewModel.selectedTimeSlot.collectAsState()
    val calendarState = rememberSheetState()
    val screen by viewModel.screen.collectAsStateWithLifecycle()
    val expanded by viewModel.isExpanded.collectAsStateWithLifecycle()
    val shop by viewModel.shop.collectAsState()
    val services by viewModel.services.collectAsStateWithLifecycle()
    val availability by viewModel.availableBookingByDay.collectAsStateWithLifecycle()
    LaunchedEffect(screen) {
        if (screen != "") {
            navController?.navigate(screen)
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
    Scaffold(
        topBar = {
            TopAppBarComponent(
                text = "Edit/Create Booking",
                onDismissRequest = viewModel::dismissMenu,
                expanded = expanded,
                expandFunction = viewModel::expandedFun,
                onClickMenus = listOf(viewModel::signOut, {})

            )
        },
    ) { innerPadding ->

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
                    viewModel.getAvailableBookingByDay()
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
