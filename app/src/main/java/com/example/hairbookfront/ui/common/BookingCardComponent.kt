package com.example.hairbookfront.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.hairbookfront.domain.entities.BarberShop
import com.example.hairbookfront.domain.entities.Booking
import com.example.hairbookfront.domain.entities.Service
import com.example.hairbookfront.ui.Dimens


@Composable
fun BookingsList(
    isCustomer: Boolean,
    bookings: List<Booking>?,
    services: List<Service>?,
    numberOfOptions: Int = 2,
    optionsIcons: List<ImageVector> = listOf(Icons.Filled.Edit, Icons.Filled.Delete),
    optionFunctions: List<(Booking) -> Unit>
) {
    bookings?.let {
        LazyColumn {
            items(bookings) { booking ->
                val service = services?.find { it.serviceId == booking.serviceId }
                service?.let {
                    BookingCardComponent(
                        isCustomer = isCustomer,
                        booking = booking,
                        service = it,
                        numberOfOptions = numberOfOptions,
                        optionsIcons = optionsIcons,
                        optionFunctions = optionFunctions
                    )
                }
            }
        }
    }
}

@Composable
fun BookingCardComponent(
    isCustomer: Boolean,
    booking: Booking,
    service: Service,
    numberOfOptions: Int = 2,
    optionsIcons: List<ImageVector> = listOf(Icons.Filled.Edit, Icons.Filled.Delete),
    optionFunctions: List<(Booking) -> Unit>
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = Dimens.smallPadding1
        ),
        modifier = Modifier
            .fillMaxWidth()
            .alpha(0.7f)
            .clip(MaterialTheme.shapes.medium)
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF3CC90),
        ),
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Shop Name: ${booking.barberShopName}",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center, // Add this line
            modifier = Modifier.fillMaxWidth() // Add this line
        )
        if (isCustomer) {
            Text(
                text = "Barber Name: ${booking.barberName} ",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        } else Text(
            text = "Customer Name: ${booking.customerName}",
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "Service: ${service.serviceName}",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "Price: ${service.price}", color = Color.Black, fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "Duration: ${service.duration}",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "Date: ${booking.date}", color = Color.Black, fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            for (i in 1..numberOfOptions) {
                ButtonComponent(
                    modifier = Modifier
                        .width(50.dp)
                        .weight(1f),
                    text = "",
                    icon = optionsIcons[i - 1],
                    onClick = {
                        optionFunctions[i - 1](booking)
                    }
                )
            }
        }
    }
}
