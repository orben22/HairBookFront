package com.example.hairbookfront.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.hairbookfront.domain.entities.BarberShop
import com.example.hairbookfront.ui.Dimens

@Composable
fun BarberShopList(
    barberShops: List<BarberShop>?,
    editable: Boolean = false,
    onBarberShopClick: (BarberShop) -> Unit = {}
) {
    barberShops?.let {
        LazyColumn {
            items(barberShops) { barberShop ->
                BarberShopItem(barberShop = barberShop, editable, onBarberShopClick)
            }
        }
    }
}

@Composable
fun BarberShopItem(
    barberShop: BarberShop,
    editable: Boolean,
    onBarberShopClick: (BarberShop) -> Unit
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = Dimens.smallPadding1
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = Dimens.smallPadding1,
                bottom = Dimens.smallPadding1,
                start = Dimens.smallPadding3,
                end = Dimens.smallPadding3
            )
            .clickable { onBarberShopClick(barberShop) }
    ) {
        Text(
            modifier = Modifier.padding(
                top = Dimens.smallPadding1,
                start = Dimens.smallPadding3
            ),
            text = barberShop.barberShopName,
            fontWeight = FontWeight.Bold,
            fontSize = Dimens.fontLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            modifier = Modifier.padding(
                top = Dimens.smallPadding1,
                start = Dimens.smallPadding3
            ),
            text = barberShop.description,
            fontSize = Dimens.fontSmall,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            modifier = Modifier.padding(
                top = Dimens.smallPadding1,
                start = Dimens.smallPadding3
            ),
            text = "Location: ${barberShop.location}",
            fontSize = Dimens.fontMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            modifier = Modifier.padding(
                top = Dimens.smallPadding1,
                start = Dimens.smallPadding3, bottom = Dimens.smallPadding1
            ),
            text = "Barber: ${barberShop.barberShopName}",
            fontSize = Dimens.fontMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary
        )
        if (editable) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.smallPadding3),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { /* TODO: Handle edit action */ }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                }
                IconButton(onClick = { /* TODO: Handle edit action */ }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Remove")
                }
            }
        }
    }
}

