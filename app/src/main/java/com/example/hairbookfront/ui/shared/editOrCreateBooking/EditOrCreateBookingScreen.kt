package com.example.hairbookfront.ui.shared.editOrCreateBooking

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.hairbookfront.domain.entities.Service
import com.example.hairbookfront.theme.HairBookFrontTheme
import com.example.hairbookfront.ui.common.BottomAppBarComponent
import com.example.hairbookfront.ui.common.DatePickerComponent
import com.example.hairbookfront.ui.common.ServiceCard
import com.example.hairbookfront.ui.common.TimeSlotsPicker
import com.example.hairbookfront.ui.common.TopAppBarComponent
import com.maxkeppeker.sheets.core.models.base.rememberSheetState


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditOrCreateBookingScreen(
    viewModel: EditOrCreateBookingViewModel = hiltViewModel(),
    navController: NavController? = null,
) {
    val selectedService by viewModel.selectedService.collectAsStateWithLifecycle()
    val selectedDate by viewModel.selectedDate.collectAsState()
    val selectedTimeSlot by viewModel.selectedTimeSlot.collectAsState()
    val calendarState = rememberSheetState()

    Scaffold(
        topBar = {
            TopAppBarComponent(text = "Book hair cut")
        },
        bottomBar = {
            BottomAppBarComponent()
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(text = "Select a service")
            }
            // Directly create the ServiceCard for each service
            items(services.size) { serviceIndex ->
                ServiceCard(
                    services[serviceIndex],
                    services[serviceIndex].serviceId == selectedService?.serviceId
                ) { clickedService ->
                    viewModel.onServiceSelected(clickedService)
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
                    TimeSlotsPicker(selectedTimeSlot, viewModel::onTimeSlotSelected)
                }
            }
        }
        DatePickerComponent(getDisabledDates = { listOf() },
            calendarState = calendarState,
            onDateSelected = { viewModel.onDateSelected(it) })
    }
}

val services = listOf(
    Service("HairCut", 10f, 30f, "1", "1"),
    Service("HairCut", 10f, 30f, "1", "2"),
    Service("HairCut", 10f, 30f, "1", "3"),
    Service("HairCut", 10f, 30f, "1", "4")
)

