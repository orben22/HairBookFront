package com.example.hairbookfront.ui.customer.reviewsHistory

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.hairbookfront.ui.common.BottomAppBarComponent
import com.example.hairbookfront.ui.common.TopAppBarComponent
import timber.log.Timber


@Composable
fun ReviewsHistoryScreen(
    reviewsHistoryViewModel: ReviewsHistoryViewModel = hiltViewModel(),
    navController: NavController? = null,
) {
    val screen by reviewsHistoryViewModel.screen.collectAsStateWithLifecycle()
    val expanded by reviewsHistoryViewModel.isExpanded.collectAsStateWithLifecycle()
    LaunchedEffect(screen) {
        if (screen != "") {
            Timber.d("navigating....")
            navController?.navigate(screen)
        }
    }
    Scaffold(
        topBar = {
            TopAppBarComponent(
                text = "Reviews History",
                onDismissRequest = reviewsHistoryViewModel::dismissMenu,
                expanded = expanded,
                expandFunction = reviewsHistoryViewModel::expandedFun,
                onClickMenus = listOf(
                    reviewsHistoryViewModel::profileClicked,
                    reviewsHistoryViewModel::signOut
                )
            )
        },
        bottomBar = {
            BottomAppBarComponent()
        },
        content = { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {

            }
        })
}