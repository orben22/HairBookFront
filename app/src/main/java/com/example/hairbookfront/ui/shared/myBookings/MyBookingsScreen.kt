package com.example.hairbookfront.ui.shared.myBookings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.hairbookfront.HairBookApplication
import com.example.hairbookfront.ui.common.BookingsList
import com.example.hairbookfront.ui.common.BottomAppBarComponent
import com.example.hairbookfront.ui.common.TopAppBarComponent
import com.example.hairbookfront.util.Constants
import timber.log.Timber


@Composable
fun MyBookingsScreen(
    viewModel: MyBookingsViewModel = hiltViewModel(),
    navController: NavController = rememberNavController()
) {
    val screen by viewModel.screen.collectAsStateWithLifecycle()
    val expanded by viewModel.isExpanded.collectAsStateWithLifecycle()
    val role by viewModel.role.collectAsState(initial = "")
    val bookingsList by viewModel.bookings.collectAsStateWithLifecycle()
    val servicesList by viewModel.services.collectAsStateWithLifecycle()
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
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val context = LocalContext.current
    val myApp = context.applicationContext as HairBookApplication
    val randomBackground = remember { myApp.getRandomBackground() }
    val drawable = context.resources.getDrawable(randomBackground, null)
    val bitmap = drawable.toBitmap()
    val imageBitmap = bitmap.asImageBitmap()
    LaunchedEffect(currentRoute) {
        viewModel.refreshData()
    }
    Scaffold(
        topBar = {
            TopAppBarComponent(
                text = "My Bookings",
                onDismissRequest = viewModel::dismissMenu,
                expanded = expanded,
                expandFunction = viewModel::expandedFun,
                onClickMenus = listOf(
                    viewModel::profileClicked,
                    viewModel::signOut),
                onClickBackArrow = viewModel::onBackClicked
            )
        },
    ) { innerPadding ->
        Image(
            bitmap = imageBitmap,
            contentDescription = null,
            modifier = Modifier.fillMaxSize().alpha(0.4f),
            contentScale = ContentScale.Crop // This will make the image scale to fill the entire screen
        )
        Column(modifier = Modifier.padding(innerPadding)) {
            if (role == Constants.BarberRole) {
                BookingsList(
                    isCustomer = false,
                    bookings = bookingsList,
                    services = servicesList,
                    numberOfOptions = 1,
                    optionsIcons = listOf(Icons.Filled.Delete),
                    optionFunctions = listOf { viewModel.deleteBookings(it) }
                )
            }
            if (role == Constants.CustomerRole) {
                BookingsList(
                    isCustomer = true,
                    bookings = bookingsList,
                    services = servicesList,
                    optionFunctions = listOf({ viewModel.editBookings(it) },
                        { viewModel.deleteBookings(it) })
                )
            }
        }
    }
}