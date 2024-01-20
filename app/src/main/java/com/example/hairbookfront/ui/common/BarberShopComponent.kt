package com.example.hairbookfront.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.hairbookfront.ui.Dimens
import com.example.hairbookfront.ui.searchShop.BarberShopsData
import com.example.hairbookfront.ui.searchShop.barbershops

@Composable
fun BarberShopList() {
    LazyColumn {
        items(barbershops) { barberShop ->
            BarberShopItem(barberShop = barberShop)
        }
    }
}

@Composable
fun BarberShopItem(barberShop: BarberShopsData) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = Dimens.smallPadding1
        ),
        modifier = Modifier.fillMaxWidth().padding(
            top = Dimens.smallPadding1,
            bottom = Dimens.smallPadding1,
            start = Dimens.smallPadding3,
            end = Dimens.smallPadding3
        )
    ) {
        Text(
            modifier = Modifier.padding(top = Dimens.smallPadding1,
                start = Dimens.smallPadding3
            ),
            text = barberShop.barbershopName,
            fontWeight = FontWeight.Bold,
            fontSize = Dimens.fontLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            modifier = Modifier.padding(top = Dimens.smallPadding1,
                start = Dimens.smallPadding3
            ),
            text = barberShop.description,
            fontSize = Dimens.fontSmall,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            modifier = Modifier.padding(top = Dimens.smallPadding1,
                start = Dimens.smallPadding3
            ),
            text = "Location: ${barberShop.location}",
            fontSize = Dimens.fontMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            modifier = Modifier.padding(top = Dimens.smallPadding1,
                start = Dimens.smallPadding3, bottom = Dimens.smallPadding1
            ),
            text = "Barber: ${barberShop.barberName}",
            fontSize = Dimens.fontMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}
