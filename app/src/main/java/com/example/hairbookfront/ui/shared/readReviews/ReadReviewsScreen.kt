package com.example.hairbookfront.ui.shared.readReviews

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.hairbookfront.domain.entities.Review
import com.example.hairbookfront.theme.HairBookFrontTheme
import com.example.hairbookfront.ui.common.ReviewsList
import com.example.hairbookfront.ui.common.TopAppBarComponent
import timber.log.Timber

@Composable
fun ReadReviewsScreen(
    readReviewsViewModel: ReadReviewsViewModel = hiltViewModel(),
    navController: NavController? = null,
) {
    val screen by readReviewsViewModel.screen.collectAsStateWithLifecycle()
    val expanded by readReviewsViewModel.isExpanded.collectAsStateWithLifecycle()
    LaunchedEffect(screen) {
        if (screen != "") {
            Timber.d("navigating....$screen")
            navController?.navigate(screen)
        }
    }
    Column {
        TopAppBarComponent(
            text = "Reviews",
            onDismissRequest = readReviewsViewModel::dismissMenu,
            expanded = expanded,
            expandFunction = readReviewsViewModel::expandedFun,
            onClickMenus = listOf(
                readReviewsViewModel::profileClicked,
                readReviewsViewModel::signOut
            )
        )
        ReviewsList(reviews = listOf(review1, review2, review3, review4, review5, review6), editable = listOf(true, false, true, false, true, false))
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

