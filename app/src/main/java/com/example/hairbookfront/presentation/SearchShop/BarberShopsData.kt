package com.example.hairbookfront.presentation.SearchShop

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hairbookfront.presentation.Dimens.fontLarge
import com.example.hairbookfront.presentation.Dimens.fontMedium
import com.example.hairbookfront.presentation.Dimens.fontSmall
import com.example.hairbookfront.presentation.Dimens.smallPadding1
import com.example.hairbookfront.presentation.Dimens.smallPadding3

data class BarberShopsData(
    val barbershopName: String,
    val description: String,
    val location: String,
    val barbername: String
)

val barbershops = listOf(
    BarberShopsData(
        barbershopName = "Orel Barbershop",
        description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
        location = "Oranit",
        barbername = "Orel"
    ),
    BarberShopsData(
        barbershopName = "Nahman Barbershop",
        description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
        location = "Ariel",
        barbername = "Nahman"
    ),
    BarberShopsData(
        barbershopName = "Moshe Barbershop",
        description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
        location = "Ariel",
        barbername = "Moshe"
    )
)



