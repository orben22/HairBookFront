package com.example.hairbookfront.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TimeSlotsPicker(
    selectedTimeSlot: String,
    onTimeSlotSelected: (String) -> Unit,
    timeSlots: List<String> = listOf(
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
) {
    ElevatedCard(
        modifier = Modifier.padding(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
    ) {
        Column {
            timeSlots.chunked(3).forEach { rowSlots ->
                Row {
                    rowSlots.forEach { timeSlot ->
                        Button(
                            onClick = { onTimeSlotSelected(timeSlot) },
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