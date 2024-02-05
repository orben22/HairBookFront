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
import androidx.navigation.compose.rememberNavController
import com.example.hairbookfront.domain.entities.Review
import com.example.hairbookfront.theme.HairBookFrontTheme
import com.example.hairbookfront.ui.common.ReviewsList
import com.example.hairbookfront.ui.common.TopAppBarComponent
import timber.log.Timber

@Composable
fun ReadReviewsScreen(
    viewModel: ReadReviewsViewModel = hiltViewModel(),
    navController: NavController = rememberNavController()
) {
    val screen by viewModel.screen.collectAsStateWithLifecycle()
    val expanded by viewModel.isExpanded.collectAsStateWithLifecycle()
    val reviews by viewModel.reviews.collectAsStateWithLifecycle()
    val role by viewModel.role.collectAsStateWithLifecycle()
    val lastScreen by viewModel.lastScreen.collectAsStateWithLifecycle()
    LaunchedEffect(screen) {
        if (screen != "") {
            viewModel.clearScreen()
            navController.navigate(screen)
        }
    }
    LaunchedEffect(lastScreen) {
        if (lastScreen) {
            navController.popBackStack()
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
            ),
            onClickBackArrow = viewModel::onBackClicked
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

