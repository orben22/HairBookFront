package com.example.hairbookfront.presentation.CreateBarberShop

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.hairbookfront.ui.Dimens
import com.example.hairbookfront.ui.common.AppTextField
import com.example.hairbookfront.ui.common.TopAppBarHairBook
import com.example.hairbookfront.ui.barber.createBarberShop.CreateBarberShopViewModel
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateBarberShopScreen(
    viewModel: CreateBarberShopViewModel = hiltViewModel(),
    navController: NavHostController? = null
) {

    val context = LocalContext.current
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
    val barberShopName by viewModel.barberShopName.collectAsStateWithLifecycle()
    val barberShopError by viewModel.barberShopNameError.collectAsStateWithLifecycle()
    val description by viewModel.barberShopDescription.collectAsStateWithLifecycle()
    val descriptionError by viewModel.barberShopDescriptionError.collectAsStateWithLifecycle()
    val location by viewModel.barberShopAddress.collectAsStateWithLifecycle()
    val locationError by viewModel.barberShopAddressError.collectAsStateWithLifecycle()
    val barberShopPhoneNumber by viewModel.barberShopPhoneNumber.collectAsStateWithLifecycle()
    val barberShopPhoneNumberError by viewModel.barberShopPhoneNumberError.collectAsStateWithLifecycle()
    val daysOfWeek by viewModel.daysOfWeek.collectAsStateWithLifecycle()


    val shownClocks by viewModel.shownClocks.collectAsStateWithLifecycle()


    val days = viewModel.days
    val states = listOf(
        rememberSheetState(),
        rememberSheetState(),
        rememberSheetState(),
        rememberSheetState(),
        rememberSheetState(),
        rememberSheetState(),
        rememberSheetState()
    )

    val statesEnd = listOf(
        rememberSheetState(),
        rememberSheetState(),
        rememberSheetState(),
        rememberSheetState(),
        rememberSheetState(),
        rememberSheetState(),
        rememberSheetState()
    )

    for (i in daysOfWeek.indices) {
        if (daysOfWeek[i]) {
            ClockDialog(state = states[i],
                config = ClockConfig(
                    is24HourFormat = true,
                    defaultTime = LocalTime.MIN.plusHours(8),
                ),
                selection = ClockSelection.HoursMinutes { hours, minutes ->
                    val min = if (minutes < 10) "0$minutes" else minutes
                    viewModel.setStartTimeFunctions[i]("$hours:$min")
                    statesEnd[i].show()
                })

            if (!shownClocks[i]) {
                states[i].show()
                viewModel.setShownClocks(i, true)
            }
            ClockDialog(state = statesEnd[i],
                config = ClockConfig(
                    is24HourFormat = true,
                    defaultTime = LocalTime.MIN.plusHours(9),
                ),
                selection = ClockSelection.HoursMinutes { hours, minutes ->
                    val min = if (minutes < 10) "0$minutes" else minutes
                    viewModel.setEndTimeFunctions[i]("$hours:$min")
                })
        }
    }

    Scaffold(topBar = {
        TopAppBarHairBook(text = "Create BarberShop")
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = { viewModel.isValidInput() },
        ) {
            Icon(Icons.Filled.Add, "Floating action button.")
        }
    },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AppTextField(
                    value = barberShopName,
                    placeholderText = "BarberShop Name",
                    icon = null,
                    onValueChange = { viewModel.setBarberShopName(it) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        capitalization = KeyboardCapitalization.Words,
                        keyboardType = KeyboardType.Text
                    ),
                    isError = barberShopError
                )
                AppTextField(
                    value = description,
                    placeholderText = "Write a description",
                    icon = null,
                    onValueChange = { viewModel.setBarberShopDescription(it) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        capitalization = KeyboardCapitalization.Sentences,
                        keyboardType = KeyboardType.Text
                    ),
                    isError = descriptionError
                )
                AppTextField(
                    value = location,
                    placeholderText = "Location",
                    icon = Icons.Outlined.LocationOn,
                    onValueChange = { viewModel.setBarberShopAddress(it) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        capitalization = KeyboardCapitalization.Words,
                        keyboardType = KeyboardType.Text
                    ),
                    isError = locationError
                )
                AppTextField(
                    value = barberShopPhoneNumber,
                    placeholderText = "Phone Number",
                    icon = Icons.Outlined.Phone,
                    onValueChange = { viewModel.setPhoneNumber(it) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    isError = barberShopPhoneNumberError
                )
                Text(
                    modifier = Modifier.padding(
                        top = Dimens.smallPadding1, start = Dimens.smallPadding3
                    ),
                    text = "Please choose the days you work",
                    fontWeight = FontWeight.Bold,
                    fontSize = Dimens.fontLarge,
                    color = Color.Black
                )

                ElevatedCard(
                    modifier = Modifier.padding(16.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    ),
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 80.dp),
                        modifier = Modifier.padding(16.dp)
                    ) {
                        items(days) { day ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    modifier = Modifier.padding(
                                        top = Dimens.smallPadding1,
                                        start = Dimens.smallPadding3
                                    ),
                                    text = day,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = Dimens.fontSmall,
                                    color = Color.Black
                                )
                                Checkbox(
                                    checked = daysOfWeek[days.indexOf(day)],
                                    onCheckedChange = { isChecked ->
                                        viewModel.setDayOfWeek(days.indexOf(day), isChecked)
                                        if (!isChecked) {
                                            viewModel.setShownClocks(
                                                days.indexOf(day),
                                                false
                                            )
                                        }
                                    },
                                    colors = CheckboxDefaults.colors(Color.Green)
                                )
                            }
                        }
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp)
                ) {
                    items(days) { day ->
                        if (daysOfWeek[days.indexOf(day)]) {
                            Text(
                                text = "$day: ${viewModel.timesOfWeek[days.indexOf(day)].first.collectAsState().value} - ${
                                    viewModel.timesOfWeek[days.indexOf(day)].second.collectAsState().value
                                }"
                            )
                        }
                    }
                }
            }
        }
    )
}

//@RequiresApi(Build.VERSION_CODES.O)
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AppTimePickerv2() {
//    val context = LocalContext.current
//    val calenderState = rememberSheetState()
//    val clockState = rememberSheetState()
//    CalendarDialog(state = calenderState,
//        config = CalendarConfig(
//            monthSelection = true,
//            yearSelection = true,
//            disabledDates = getDisabledDates()
//        ),
//        selection = CalendarSelection.Date {
//            Toast.makeText(
//                context,
//                it.toString(),
//                Toast.LENGTH_SHORT
//            ).show()
//        })
//    ClockDialog(state = clockState,
//        config = ClockConfig(
//            is24HourFormat = true,
//            defaultTime = LocalTime.MIN,
//        ),
//        selection = ClockSelection.HoursMinutes { hours, minutes ->
//            Toast.makeText(
//                context,
//                roundUpTime("$hours:$minutes"),
//                Toast.LENGTH_SHORT
//            ).show()
//        })
//    Column(
//        modifier = Modifier
//            .fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Button(onClick = { calenderState.show() }) {
//            Text(text = "Pick Date")
//        }
//        Spacer(modifier = Modifier.height(16.dp))
//
//        TimeSlotsPicker()
//    }
//}

@Composable
fun TimeSlotsPicker() {
    val timeSlots = listOf(
        "10:00",
        "10:30",
        "11:00",
        "11:30",
        "12:00",
        "12:30",
        "13:00",
        "13:30",
        "14:00",
        "14:30",
        "15:00"
    )
    var selectedTimeSlot by remember { mutableStateOf("") }

    ElevatedCard(
        modifier = Modifier.padding(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),

        ) {
        LazyColumn {
            items(timeSlots.chunked(3)) { rowSlots ->
                LazyRow {
                    items(rowSlots) { timeSlot ->
                        Button(
                            onClick = { selectedTimeSlot = timeSlot },
                            modifier = Modifier.padding(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                contentColor = if (timeSlot == selectedTimeSlot) Color(0xFF03A9F4) else Color.White
                            )
                        ) {
                            Text(text = timeSlot)
                        }
                    }
                }
            }
        }
    }
    Text(text = "Selected time slot: $selectedTimeSlot")
}

//@RequiresApi(Build.VERSION_CODES.O)
//fun getDisabledDates(): List<LocalDate> {
//    val workingDaysList = listOf(1, 0, 0, 0, 1, 0, 0)
//    val daysOfWeek = listOf(
//        DayOfWeek.SUNDAY,
//        DayOfWeek.MONDAY,
//        DayOfWeek.TUESDAY,
//        DayOfWeek.WEDNESDAY,
//        DayOfWeek.THURSDAY,
//        DayOfWeek.FRIDAY,
//        DayOfWeek.SATURDAY
//    )
//    val workingDays = workingDaysList.mapIndexedNotNull { index, isWorkingDay ->
//        if (isWorkingDay == 1) daysOfWeek[index] else null
//    }
//    val disabledDates = mutableListOf<LocalDate>()
//    var date = LocalDate.now()
//    while (date.year == LocalDate.now().year) {
//        if (!workingDays.contains(date.dayOfWeek)) {
//            disabledDates.add(date)
//        }
//        date = date.plusDays(1)
//    }
//
//    return disabledDates
//}

@RequiresApi(Build.VERSION_CODES.O)
fun roundUpTime(time: String): String {
    val formatter = DateTimeFormatter.ofPattern("H:mm")
    val parsedTime = LocalTime.parse(time, formatter)
    return if (parsedTime.minute > 45) {
        val roundedTime = parsedTime.withMinute(0).plusHours(1)
        roundedTime.format(formatter)
    } else if (parsedTime.minute in 15 until 45) {
        val roundedTime = parsedTime.withMinute(30)
        roundedTime.format(formatter)
    } else {
        val roundedTime = parsedTime.withMinute(0)
        roundedTime.format(formatter)
    }
}
