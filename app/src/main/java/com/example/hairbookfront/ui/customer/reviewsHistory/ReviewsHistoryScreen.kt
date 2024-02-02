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
    viewModel: ReviewsHistoryViewModel = hiltViewModel(),
    navController: NavController? = null,
) {
    val screen by viewModel.screen.collectAsStateWithLifecycle()
    val expanded by viewModel.isExpanded.collectAsStateWithLifecycle()
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
                onDismissRequest = viewModel::dismissMenu,
                expanded = expanded,
                expandFunction = viewModel::expandedFun,
                onClickMenus = listOf(
                    viewModel::profileClicked,
                    viewModel::signOut
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