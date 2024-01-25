package com.example.hairbookfront.ui.shared.editOrCreateReview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.hairbookfront.ui.common.BottomAppBarComponent
import com.example.hairbookfront.ui.common.TopAppBarComponent


@Composable
fun EditOrCreateReviewScreen(
    viewModel: EditOrCreateReviewViewModel = hiltViewModel(),
    navController: NavController? = null,
) {

    Scaffold(
        topBar = {
            TopAppBarComponent(text = "Post Review")
        },
        bottomBar = {
            BottomAppBarComponent()
        },
        content = { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {

            }
        })
}