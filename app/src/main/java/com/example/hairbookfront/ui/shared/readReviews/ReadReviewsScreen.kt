package com.example.hairbookfront.ui.shared.readReviews

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.hairbookfront.domain.entities.Review
import com.example.hairbookfront.theme.HairBookFrontTheme
import com.example.hairbookfront.ui.common.ReviewsList
import com.example.hairbookfront.ui.common.TopAppBarComponent

@Composable
fun ReadReviewsScreen(
    viewModel: ReadReviewsViewModel = hiltViewModel(),
    navController: NavController? = null,
) {
    Column {
        TopAppBarComponent(text = "Reviews")
        ReviewsList(reviews = listOf(review1, review2, review3, review4, review5, review6))
    }
}

val review1 = Review(
    reviewId = "1",
    firstName = "firstName",
    lastName = "lastName",
    review = "review",
    rating = 3f,
    timestamp = "timestamp",
    userId = "userId",
    barberShopId = "barberShopId"
)
val review2 = Review(
    reviewId = "1",
    firstName = "firstName",
    lastName = "lastName",
    review = "review",
    rating = 4f,
    timestamp = "timestamp",
    userId = "userId",
    barberShopId = "barberShopId"
)

val review3 = Review(
    reviewId = "1",
    firstName = "firstName",
    lastName = "lastName",
    review = "review",
    rating = 5f,
    timestamp = "timestamp",
    userId = "userId",
    barberShopId = "barberShopId"
)

val review4 = Review(
    reviewId = "1",
    firstName = "firstName",
    lastName = "lastName",
    review = "review",
    rating = 2f,
    timestamp = "timestamp",
    userId = "userId",
    barberShopId = "barberShopId"
)

val review5 = Review(
    reviewId = "1",
    firstName = "firstName",
    lastName = "lastName",
    review = "review",
    rating = 2.6f,
    timestamp = "timestamp",
    userId = "userId",
    barberShopId = "barberShopId"
)

val review6 = Review(
    reviewId = "1",
    firstName = "firstName",
    lastName = "lastName",
    review = "review",
    rating = 2.1f,
    timestamp = "timestamp",
    userId = "userId",
    barberShopId = "barberShopId"
)

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SignUpBarberScreenPreviewDark() {
    HairBookFrontTheme {
        ReadReviewsScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun SignUpBarberScreenPreviewLight() {
    HairBookFrontTheme {
        ReadReviewsScreen()
    }
}

