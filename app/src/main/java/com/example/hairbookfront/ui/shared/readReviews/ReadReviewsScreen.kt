package com.example.hairbookfront.ui.shared.readReviews

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.hairbookfront.HairBookApplication
import com.example.hairbookfront.domain.entities.Review
import com.example.hairbookfront.theme.HairBookFrontTheme
import com.example.hairbookfront.ui.common.DialogComponent
import com.example.hairbookfront.ui.common.ReviewsList
import com.example.hairbookfront.ui.common.TopAppBarComponent
import com.example.hairbookfront.util.Constants
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
    val showOrHideDeleteDialog by viewModel.showOrHideDeleteDialog.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val myApp = context.applicationContext as HairBookApplication
    val randomBackground = remember { myApp.getRandomBackground() }
    val drawable = context.resources.getDrawable(randomBackground, null)
    val bitmap = drawable.toBitmap()
    val imageBitmap = bitmap.asImageBitmap()
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
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    LaunchedEffect(currentRoute) {
        viewModel.refreshData()
    }
    Scaffold(
        topBar = {
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
        }
    ) { innerPadding ->
        Image(
            bitmap = imageBitmap,
            contentDescription = null,
            modifier = Modifier.fillMaxSize().alpha(0.4f),
            contentScale = ContentScale.Crop // This will make the image scale to fill the entire screen
        )
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            if (reviews != null) {
                Timber.d("Reviews: ${reviews!!.size}")
                if (role == Constants.CustomerRole) {
                    val editable= reviews!!.map { true }
                    ReviewsList(
                        reviews = reviews!!, editable = editable, role = role,
                        onClickFunctions = listOf(
                            viewModel::editReview,
                            viewModel::showOrHideDeleteDialog
                        )
                    )
                } else {
                    val editable= reviews!!.map { false }
                    ReviewsList(
                        reviews = reviews!!, editable = editable, role = role,
                        onClickFunctions = listOf(viewModel::showOrHideDeleteDialog)
                    )
                }
            } else {
                Text(
                    text = "No reviews found",
                    modifier = androidx.compose.ui.Modifier.padding(16.dp)
                )
            }
        }
        if (showOrHideDeleteDialog) {
            DialogComponent(
                dialogTitle = "Delete confirmation",
                dialogText = "Are you sure you want to delete this review?",
                confirmFunctions = listOf(viewModel::deleteReview, viewModel::onDismissRequest),
                onDismissRequest = viewModel::onDismissRequest
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

