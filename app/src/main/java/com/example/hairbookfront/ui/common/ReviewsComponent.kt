package com.example.hairbookfront.ui.common

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
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.hairbookfront.domain.entities.BarberShop
import com.example.hairbookfront.domain.entities.Review
import com.example.hairbookfront.ui.Dimens

@Composable
fun ReviewsList(reviews: List<Review>?, editable: Boolean = false) {
    reviews?.let {
        LazyColumn {
            items(reviews) { review ->
                ReviewItem(review = review, editable)
            }
        }
    }
}

@Composable
fun ReviewItem(review: Review, editable: Boolean) {
    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = Dimens.smallPadding1,
                bottom = Dimens.smallPadding1,
                start = Dimens.smallPadding3,
                end = Dimens.smallPadding3
            )
    ) {
        Text(
            modifier = Modifier.padding(
                top = Dimens.smallPadding1,
                start = Dimens.smallPadding3
            ),
            text = "From: "+review.firstName+" "+review.lastName,
            fontWeight = FontWeight.Bold,
            fontSize = Dimens.fontSmall,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            modifier = Modifier.padding(
                top = Dimens.smallPadding1,
                start = Dimens.smallPadding3
            ),
            text = review.review,
            fontSize = Dimens.fontLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            modifier = Modifier.padding(
                top = Dimens.smallPadding1,
                start = Dimens.smallPadding3
            ),
            text = "Rating: " + review.rating,
            fontSize = Dimens.fontSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            modifier = Modifier.padding(
                top = Dimens.smallPadding1,
                start = Dimens.smallPadding3, bottom = Dimens.smallPadding1
            ),
            text = "Time: "+ review.timestamp,
            fontSize = Dimens.fontSmall,
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
