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
import com.maxkeppeker.sheets.core.models.base.SheetState
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarTimeline
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerComponent(
    getDisabledDates: () -> List<LocalDate>,
    calendarState: SheetState = rememberSheetState(),
    onDateSelected: (String) -> Unit = {},
) {
    CalendarDialog(state = calendarState,

        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true,
            disabledDates = getDisabledDates(),
            disabledTimeline = CalendarTimeline.PAST,
        ),
        selection = CalendarSelection.Date { selectedDate ->
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            val formattedDate = selectedDate.format(formatter)
            onDateSelected(formattedDate)
        })
}
