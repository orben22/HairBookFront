package com.example.hairbookfront.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.hairbookfront.domain.entities.Service

@Composable
fun ServiceCard(
    service: Service,
    isSelected: Boolean,
    onServiceClick: (Service) -> Unit,
    isEditable: Boolean = false,
    onServiceNameChange: (String) -> Unit = {},
    onPriceChange: (String) -> Unit = {},
    onDurationChange: (String) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onServiceClick(service) }
            .border(
                width = if (isSelected) 2.dp else 0.dp,
                color = if (isSelected) Color.Blue else Color.Transparent,
                shape = MaterialTheme.shapes.medium
            ),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isEditable) {
                TextField(
                    value = service.serviceName,
                    onValueChange = onServiceNameChange,
                    label = { Text("Service Name") }
                )
            } else {
                Text(text = service.serviceName, style = MaterialTheme.typography.headlineLarge)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (isEditable) {
                    TextField(
                        value = service.price.toString(),
                        onValueChange = onPriceChange,
                        label = { Text("Price") }
                    )
                    TextField(
                        value = service.duration.toString(),
                        onValueChange = onDurationChange,
                        label = { Text("Duration") }
                    )
                } else {
                    Text(text = "Price: ${service.price}")
                    Text(text = "Duration: ${service.duration}")
                }
            }
        }
    }
}

