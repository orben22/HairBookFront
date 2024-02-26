package com.example.hairbookfront.ui.customer.customerHome

import android.content.res.Configuration
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.hairbookfront.HairBookApplication
import com.example.hairbookfront.ui.common.TextFieldComponent
import com.example.hairbookfront.ui.common.BarberShopList
import com.example.hairbookfront.ui.common.TopAppBarComponent
import com.example.hairbookfront.theme.HairBookFrontTheme
import com.example.hairbookfront.ui.common.BottomAppBarComponent
import timber.log.Timber

@Composable
fun CustomerHomeScreen(
    viewModel: CustomerHomeViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()

) {
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()
    val barberShops by viewModel.barberShops.collectAsStateWithLifecycle()
    val screen by viewModel.screen.collectAsStateWithLifecycle()
    val expanded by viewModel.isExpanded.collectAsStateWithLifecycle()
    val lastScreen by viewModel.lastScreen.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val myApp = context.applicationContext as HairBookApplication
    val randomBackground = remember { myApp.getRandomBackground() }
    val drawable = context.resources.getDrawable(randomBackground, null)
    val drawableName = context.resources.getResourceEntryName(randomBackground)
    println("Drawable Name: $drawableName")

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
                "Search Shop",
                onDismissRequest = viewModel::dismissMenu,
                expanded = expanded,
                expandFunction = viewModel::expandedFun,
                onClickMenus = listOf(viewModel::profileClicked, viewModel::signOut)
            )
        },bottomBar = {
            BottomAppBarComponent(
                textToIcon = listOf("My Bookings", "My Reviews"),
                icons = listOf(Icons.Default.List, Icons.Default.Edit),
                onClickFunctions = listOf(viewModel::viewMyBookings,
                    viewModel::viewMyReviews)
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
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextFieldComponent(
                    value = searchText,
                    placeholderText = "Search",
                    icon = Icons.Outlined.Search,
                    onValueChange = viewModel::onSearchTextChanged,
                )
                BarberShopList(
                    barberShops,
                    onBarberShopClick = viewModel::onBarberShopClicked
                )
            }
        }

    )

}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CustomerHomeScreenPreviewDark() {
    HairBookFrontTheme {
        CustomerHomeScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun CustomerHomeScreenPreviewLight() {
    HairBookFrontTheme {
        CustomerHomeScreen()
    }
}