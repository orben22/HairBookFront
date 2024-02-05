package com.example.hairbookfront.ui.barber.barberDetails


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.hairbookfront.ui.Dimens.distanceFromLeft
import com.example.hairbookfront.ui.Dimens.distanceFromBottom
import com.example.hairbookfront.ui.common.BarberShopList
import com.example.hairbookfront.ui.common.BottomAppBarComponent
import com.example.hairbookfront.ui.common.DialogComponent
import com.example.hairbookfront.ui.common.TopAppBarComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarberDetailsScreen(
    viewModel: BarberDetailsViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {

    val yearsOfExperience by viewModel.getYearsOfExperience()
        .collectAsState(initial = 0)
    val firstname by viewModel.getFirstName().collectAsState(initial = "")
    val lastname by viewModel.getLastName().collectAsState(initial = "")
    val email by viewModel.getEmail().collectAsState(initial = "")
    val myShops by viewModel.myShops.collectAsStateWithLifecycle()
    val screen by viewModel.screen.collectAsStateWithLifecycle()
    val expanded by viewModel.isExpanded.collectAsStateWithLifecycle()
    val showOrHideDeleteDialog by viewModel.showOrHideDeleteDialog.collectAsStateWithLifecycle()
    val lastScreen by viewModel.lastScreen.collectAsStateWithLifecycle()
    LaunchedEffect(screen) {
        if (screen != "") {
            navController?.navigate(screen)
        }
    }
    LaunchedEffect(lastScreen) {
        if (lastScreen) {
            navController?.popBackStack()
        }
    }
    Scaffold(
        topBar = {
            TopAppBarComponent(
                text = "Barber Details",
                onDismissRequest = viewModel::dismissMenu,
                expanded = expanded,
                expandFunction = viewModel::expandedFun,
                suggestions = listOf("Sign Out"),
                onClickMenus = listOf(viewModel::signOut),
                onClickBackArrow = viewModel::onBackClicked
            )
        },
        bottomBar = {
            BottomAppBarComponent(
                textToIcon = listOf("My Bookings", "Create your new BarberShop"),
                icons = listOf(
                    Icons.Filled.List,
                    Icons.Filled.ShoppingCart
                ),
                onClickFunctions = listOf(
                    viewModel::onMyBookingClicked,
                    viewModel::onCreateBarberShopClicked
                )
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(
                        start = distanceFromLeft,
                        end = distanceFromLeft,
                        top = distanceFromBottom,
                        bottom = distanceFromBottom
                    )
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = "Full Name: $firstname $lastname",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = "Email: $email",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = "Experience: $yearsOfExperience years.",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = "My barbershops:",
                    style = MaterialTheme.typography.bodyMedium
                )
                BarberShopList(
                    barberShops = myShops, true,
                    onBarberShopClick = viewModel::onBarberShopClicked,
                    onIconsClicked = listOf(viewModel::editShop, viewModel::showOrHideDeleteDialog)

                )
            }
            if (showOrHideDeleteDialog) {
                DialogComponent(
                    dialogTitle = "Delete confirmation",
                    dialogText = "Are you sure you want to delete this shopp? all reviews and booking will be lost!",
                    confirmFunctions = listOf(viewModel::deleteShop, viewModel::onDismissRequest),
                    onDismissRequest = viewModel::onDismissRequest
                )
            }
        }
    )
}


