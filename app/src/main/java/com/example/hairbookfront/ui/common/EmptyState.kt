package com.example.hairbookfront.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.hairbookfront.R
import com.example.hairbookfront.ui.Dimens.largePadding1
import com.example.hairbookfront.ui.Dimens.smallPadding2
import com.example.hairbookfront.ui.Dimens.fontLarge
import com.example.hairbookfront.ui.Dimens.smallPadding3

@Composable
fun EmptyStateComponent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(smallPadding3),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_app_blocking_24),
            contentDescription = "empty state",
            modifier = Modifier
                .size(largePadding1)
                .padding(smallPadding2),
            tint = Color.Gray
        )
        Text(
            text = "No data found",
            style = TextStyle(
                fontSize = fontLarge,
                fontWeight = FontWeight.Normal
            )
        )
    }
}