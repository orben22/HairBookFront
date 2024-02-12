package com.example.hairbookfront.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.hairbookfront.domain.entities.Service

@Composable
fun AddServiceDialog(
    showDialog: Boolean,
    onAddService: () -> Unit,
    serviceName: String,
    onServiceNameChange: (String) -> Unit,
    serviceNameError: Boolean = false,
    servicePrice: String,
    servicePriceError: Boolean = false,
    onServicePriceChange: (String) -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        Dialog(onDismissRequest = { onDismiss() }) {
            ElevatedCard {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Add a new service",
                        modifier = Modifier
                            .padding(16.dp)
                    )
                    TextFieldComponent(
                        value = serviceName,
                        placeholderText = "Service Name",
                        icon = null,
                        onValueChange = { onServiceNameChange(it) },
                        isError = serviceNameError,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            capitalization = KeyboardCapitalization.Words,
                            keyboardType = KeyboardType.Text
                        ),

                        )
                    TextFieldComponent(
                        value = servicePrice,
                        placeholderText = "Price",
                        icon = null,
                        onValueChange = { onServicePriceChange(it) },
                        isError = servicePriceError,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        )
                    )
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Button(onClick = {
                            onAddService()
                        }) {
                            Text("Add Service")
                        }
                    }
                }
            }
        }
    }
}