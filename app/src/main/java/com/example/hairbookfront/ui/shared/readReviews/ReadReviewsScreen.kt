package com.example.hairbookfront.ui.shared.readReviews

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    viewModel: ReadReviewsViewModel = hiltViewModel(),
    navController: NavController? = null,
) {
    val screen by viewModel.screen.collectAsStateWithLifecycle()
    val expanded by viewModel.isExpanded.collectAsStateWithLifecycle()
    val reviews by viewModel.reviews.collectAsStateWithLifecycle()
    val userId by viewModel.userId.collectAsStateWithLifecycle()
    val role by viewModel.role.collectAsStateWithLifecycle()
    LaunchedEffect(screen) {
        if (screen != "") {
            Timber.d("navigating....$screen")
            navController?.navigate(screen)
        }
    }
    Column {
        TopAppBarComponent(
            text = "Reviews",
            onDismissRequest = viewModel::dismissMenu,
            expanded = expanded,
            expandFunction = viewModel::expandedFun,
            onClickMenus = listOf(
                viewModel::profileClicked,
                viewModel::signOut
            )
        )
        if (reviews.isNotEmpty()) {
//            val editableList = reviews.map { true }
            ReviewsList(
                reviews = reviews, editable = listOf(true), role = role,
                onClickFunctions = listOf(
                    viewModel::editReview,
                    viewModel::showOrHideDeleteDialog
                )
            )
        }
        else {
            Text(text= "No reviews found",
                modifier = androidx.compose.ui.Modifier.padding(16.dp)
            )
        }
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

