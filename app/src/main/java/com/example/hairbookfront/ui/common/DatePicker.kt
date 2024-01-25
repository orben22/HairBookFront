package com.example.hairbookfront.ui.common

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTimePicker(
    getDisabledDates: () -> List<LocalDate>,
) {
    val context = LocalContext.current
    val calenderState = rememberSheetState()
    CalendarDialog(state = calenderState,
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true,
            disabledDates = getDisabledDates()
        ),
        selection = CalendarSelection.Date {
            // for testing
            Toast.makeText(
                context,
                it.toString(),
                Toast.LENGTH_SHORT
            ).show()
        })
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { calenderState.show() }) {
            Text(text = "Pick Date")
        }
    }
}

//TODO: move to the viewmodel
@RequiresApi(Build.VERSION_CODES.O)
fun getDisabledDates(workingDaysList: List<Int> = listOf(1, 0, 0, 0, 1, 0, 0)): List<LocalDate> {
    val daysOfWeek = listOf(
        DayOfWeek.SUNDAY,
        DayOfWeek.MONDAY,
        DayOfWeek.TUESDAY,
        DayOfWeek.WEDNESDAY,
        DayOfWeek.THURSDAY,
        DayOfWeek.FRIDAY,
        DayOfWeek.SATURDAY
    )
    val workingDays = workingDaysList.mapIndexedNotNull { index, isWorkingDay ->
        if (isWorkingDay == 1) daysOfWeek[index] else null
    }
    val disabledDates = mutableListOf<LocalDate>()
    var date = LocalDate.now()
    while (date.year == LocalDate.now().year) {
        if (!workingDays.contains(date.dayOfWeek)) {
            disabledDates.add(date)
        }
        date = date.plusDays(1)
    }
    return disabledDates
}