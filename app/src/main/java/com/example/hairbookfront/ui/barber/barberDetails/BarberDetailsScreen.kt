package com.example.hairbookfront.ui.barber.barberDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.hairbookfront.HairBookApplication
import com.example.hairbookfront.ui.Dimens.distanceFromBottom
import com.example.hairbookfront.ui.Dimens.distanceFromLeft
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
            Image(
                bitmap = imageBitmap,
                contentDescription = null,
                modifier = Modifier.fillMaxSize().alpha(0.4f),
                contentScale = ContentScale.Crop // This will make the image scale to fill the entire screen
            )
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
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = "Email: $email",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = "Experience: $yearsOfExperience years.",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyMedium

                )
                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = "My barbershops:",
                    fontWeight = FontWeight.Bold,
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


