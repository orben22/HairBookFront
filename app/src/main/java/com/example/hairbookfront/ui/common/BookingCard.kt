package com.example.hairbookfront.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.hairbookfront.ui.userDetails.BookingData

@Composable
fun BookingCard(isCustomer : Boolean,bookingData: BookingData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),


        ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

                Text(
                    text = "Booking Details:",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black
                )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Shop Name: " + bookingData.barberShopName, color = Color.Black)
            if (isCustomer) {
                bookingData.barberName?.let { Text(text = "Barber Name:$it ", color = Color.Black) }
            }else Text(text = "Customer Name"+ bookingData.costumerName)
            Text(text = "Service: " + bookingData.service.serviceName, color = Color.Black)
            Text(text = "Price: " + bookingData.service.price, color = Color.Black)
            Text(text = "Date: " + bookingData.date, color = Color.Black)



            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Edit Button
                CustomButton(text = "", onClick = { /*TODO*/ }, icon = Icons.Filled.Edit)
                // Cancel Button
                CustomButton(text = "", onClick = { /*TODO*/ }, icon = Icons.Filled.Delete)
            }
        }
    }
}