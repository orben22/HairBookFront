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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.hairbookfront.domain.entities.Service
import com.example.hairbookfront.util.Constants

@Composable
fun ServiceCard(
    service: Service,
    isSelected: Boolean,
    onServiceClick: (Service) -> Unit,
    serviceName: String="",
    servicePrice: String="",
    serviceDuration: String="",
    isEditable: Boolean = false,
    onServiceNameChange: (String) -> Unit = {},
    onPriceChange: (String) -> Unit = {},
    onDurationChange: (String) -> Unit = {},
    mode: String = "Customer",
    onEditClick: (Service) -> Unit = {},
    onDeleteClick: () -> Unit = {},
    onAcceptClick: (Service) -> Unit = {},
    onDiscardClick: () -> Unit = {}
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
                    value = serviceName,
                    onValueChange = onServiceNameChange,
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
                        value = servicePrice,
                        onValueChange = onPriceChange,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        )
                    )
                    Text(text = "Duration: $serviceDuration")
                } else {
                    Text(text = "Price: ${service.price}")
                    Text(text = "Duration: ${service.duration}")
                }
            }

            if (mode == Constants.BarberRole) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    if (isEditable) {
                        IconButton(onClick = { onAcceptClick(service) }) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = "Accept")
                        }
                        IconButton(onClick = { onDiscardClick() }) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = "Discard")
                        }
                    } else {
                        IconButton(onClick = { onEditClick(service) }) {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                        }
                        IconButton(onClick = { onDeleteClick() }) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                        }
                    }
                }
            }
        }
    }
}
