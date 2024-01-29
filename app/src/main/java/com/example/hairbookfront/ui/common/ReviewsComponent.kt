package com.example.hairbookfront.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hairbookfront.domain.entities.Review
import com.example.hairbookfront.theme.HairBookFrontTheme
import com.example.hairbookfront.ui.Dimens

@Composable
fun ReviewsList(
    reviews: List<Review>?,
    editable: Boolean = false,
    onClickFunctions: List<() -> Unit> = listOf(
        { },
        { },
    ),
    isEditing: Boolean = false,
    onReviewChange: (String) -> Unit = {},
    onRatingChange: (String) -> Unit = {},
    mode: String = "Customer",
    isError: Boolean = false,
) {
    reviews?.let {
        Column {
            for (review in reviews) {
                ReviewItem(
                    fullName = review.firstName + " " + review.lastName,
                    review = review.review,
                    rating = review.rating.toString(),
                    timestamp = review.timestamp,
                    editable = editable,
                    onClickFunctions = onClickFunctions,
                    isEditing = isEditing,
                    onReviewChange = onReviewChange,
                    onRatingChange = onRatingChange,
                    mode = mode,
                    isError = isError
                )
            }
        }
    }
}

@Composable
fun ReviewItem(
    fullName: String,
    review: String,
    rating: String,
    editable: Boolean,
    timestamp: String,
    onClickFunctions: List<() -> Unit> = listOf(
        { },
        { },
    ),
    isEditing: Boolean = false,
    onReviewChange: (String) -> Unit = {},
    onRatingChange: (String) -> Unit = {},
    mode: String = "Customer",
    isError: Boolean = false
) {
    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.mediumPadding1)
    ) {
        Column(modifier = Modifier.padding(Dimens.mediumPadding1)) {
            Text(
                text = "From: $fullName",
                fontWeight = FontWeight.Bold,
                fontSize = Dimens.fontMedium,
            )
            Spacer(modifier = Modifier.height(Dimens.smallPadding1))
            if (isEditing) {
                Text("Review:")
                TextField(
                    value = review,
                    onValueChange = onReviewChange,
                    modifier = Modifier.padding(Dimens.smallPadding1)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Rating:")
                    TextField(
                        value = rating,
                        onValueChange = onRatingChange,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .width(80.dp)
                            .padding(Dimens.smallPadding1),
                        isError = isError
                    )
                }
            } else {
                Text(
                    text = review,
                    fontSize = Dimens.fontMedium,
                )
                Spacer(modifier = Modifier.height(Dimens.smallPadding1))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Time: " + timestamp,
                        fontSize = Dimens.fontSmall,
                        fontWeight = FontWeight.Bold,
                    )
                    RatingComponent(rating = rating.toFloat())
                }
            }
            Spacer(modifier = Modifier.height(Dimens.smallPadding1))
            if (editable && mode == "Customer") {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.smallPadding3),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (isEditing) {
                        IconButton(onClick = { onClickFunctions[0]() }) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = "Check")
                        }
                    } else {
                        IconButton(onClick = { onClickFunctions[0]() }) {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                        }
                    }
                    IconButton(onClick = { onClickFunctions[1]() }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Remove")
                    }
                }
            }
        }
    }
}

