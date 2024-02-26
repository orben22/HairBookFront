package com.example.hairbookfront.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.hairbookfront.ui.Dimens

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
    ),
    availability: List<Boolean> = List(timeSlots.size) { it != timeSlots.size - 1 }
) {
    ElevatedCard(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth().alpha(0.7f),
        elevation = CardDefaults.cardElevation(
            defaultElevation = Dimens.smallPadding1
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF3CC90),
        ),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
        ) {
            timeSlots.chunked(3).forEachIndexed { rowIndex, rowSlots ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    rowSlots.forEachIndexed { columnIndex, timeSlot ->
                        val index = rowIndex * 3 + columnIndex
                        val isAvailable = availability.getOrNull(index) ?: true
                        Button(
                            onClick = { if (isAvailable) onTimeSlotSelected(timeSlot) },
                            modifier = Modifier.padding(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                contentColor = if (timeSlot == selectedTimeSlot) Color(0xFF03A9F4) else Color.White,
                                disabledContainerColor = Color.Gray
                            ),
                            enabled = isAvailable
                        ) {
                            Text(text = timeSlot)
                        }
                    }
                }
            }
        }
    }
}