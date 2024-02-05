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
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.hairbookfront.domain.entities.Review
import com.example.hairbookfront.ui.Dimens
import com.example.hairbookfront.util.Constants

@Composable
fun ReviewsList(
    reviews: List<Review>?,
    editable: List<Boolean>,
    onClickFunctions: List<(String) -> Unit> = listOf(
        { _ -> },
        { _ -> },
    ),
    isEditing: Boolean = false,
    onReviewChange: (String) -> Unit = {},
    onRatingChange: (String) -> Unit = {},
    role: String = Constants.CustomerRole,
    isError: Boolean = false,
) {
    reviews?.let {
        val pairedList = reviews.zip(editable)
        val sortedList =
            pairedList.sortedWith(compareByDescending<Pair<Review, Boolean>> { it.second }.thenBy { it.first.timestamp })
        val sortedReviews = sortedList.map { it.first }
        val sortedEditable = sortedList.map { it.second }
        Column {
            for (i in sortedReviews.indices) {
                ReviewItem(
                    fullName = sortedReviews[i].firstName + " " + sortedReviews[i].lastName,
                    review = sortedReviews[i].review,
                    rating = sortedReviews[i].rating.toString(),
                    timestamp = sortedReviews[i].timestamp,
                    reviewId =  sortedReviews[i].reviewId!!,
                    editable = sortedEditable[i],
                    onClickFunctions = onClickFunctions,
                    isEditing = isEditing,
                    onReviewChange = onReviewChange,
                    onRatingChange = onRatingChange,
                    role = role,
                    isError = isError,
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
    reviewId: String = "",
    onClickFunctions: List<(String) -> Unit> = listOf(
        { _ -> },
        { _ -> },
    ),
    isEditing: Boolean = false,
    onReviewChange: (String) -> Unit = {},
    onRatingChange: (String) -> Unit = {},
    role: String = Constants.CustomerRole,
    isError: Boolean = false
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = Dimens.smallPadding1
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF00B0FF),
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
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(Dimens.smallPadding1))
            if (isEditing) {
                Text("Review:",
                    color = Color.Black)
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
                    Text("Rating:",
                        color = Color.Black)
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
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(Dimens.smallPadding1))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Time: $timestamp",
                        fontSize = Dimens.fontSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    RatingComponent(rating = rating.toFloat())
                }
            }
            Spacer(modifier = Modifier.height(Dimens.smallPadding1))
            if (editable && role == "Customer") {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.smallPadding3),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (isEditing) {
                        IconButton(onClick = { onClickFunctions[0](reviewId) }) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = "Check")
                        }
                    } else {
                        IconButton(onClick = { onClickFunctions[0](reviewId) }) {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                        }
                    }
                    IconButton(onClick = { onClickFunctions[1](reviewId) }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Remove")
                    }
                }
            }
        }
    }
}

