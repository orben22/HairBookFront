package com.example.hairbookfront.ui.shared.editOrCreateReview

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.hairbookfront.ui.common.ButtonComponent
import com.example.hairbookfront.ui.common.ReviewItem
import com.example.hairbookfront.ui.common.TopAppBarComponent
import timber.log.Timber


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditOrCreateReviewScreen(
    viewModel: EditOrCreateReviewViewModel = hiltViewModel(),
    navController: NavController? = null,
) {

    val context = LocalContext.current
    val screen by viewModel.screen.collectAsStateWithLifecycle()
    val expanded by viewModel.isExpanded.collectAsStateWithLifecycle()
    val mode by viewModel.mode.collectAsStateWithLifecycle()
    val review by viewModel.review.collectAsStateWithLifecycle()
    val rating by viewModel.rating.collectAsStateWithLifecycle()
    val firstName by viewModel.firstName.collectAsStateWithLifecycle()
    val lastName by viewModel.lastName.collectAsStateWithLifecycle()
    val isError by viewModel.isError.collectAsStateWithLifecycle()
    LaunchedEffect(screen) {
        if (screen != "") {
            Timber.d("navigating....$screen")
            navController?.navigate(screen)
        }
    }

    LaunchedEffect(Unit) {
        viewModel
            .toastMessage
            .collect { message ->
                Toast.makeText(
                    context,
                    message,
                    Toast.LENGTH_SHORT,
                ).show()
            }
    }

    Scaffold(
        topBar = {
            TopAppBarComponent(
                text = "$mode Review",
                onDismissRequest = viewModel::dismissMenu,
                expanded = expanded,
                expandFunction = viewModel::expandedFun,
                onClickMenus = listOf(viewModel::signOut, {})
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ReviewItem(
                    fullName = "$firstName $lastName",
                    review = review,
                    rating = rating,
                    timestamp = "",
                    editable = true,
                    isEditing = true,
                    onReviewChange = viewModel::onReviewChange,
                    onRatingChange = viewModel::onRatingChange,
                    role = mode,
                    isError = isError
                )
                ButtonComponent(
                    text = "Confirm",
                    onClick = viewModel::confirmChanges,
                )
            }
        })
}