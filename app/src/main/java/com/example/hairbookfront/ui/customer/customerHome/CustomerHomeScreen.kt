package com.example.hairbookfront.ui.customer.customerHome

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.hairbookfront.ui.common.TextFieldComponent
import com.example.hairbookfront.ui.common.BarberShopList
import com.example.hairbookfront.ui.common.TopAppBarComponent
import com.example.hairbookfront.theme.HairBookFrontTheme
import com.example.hairbookfront.ui.common.BottomAppBarComponent

@Composable
fun CustomerHomeScreen(
    customerViewModel: CustomerHomeViewModel = hiltViewModel(),
    navController: NavHostController? = null
) {
    val searchText by customerViewModel.searchText.collectAsStateWithLifecycle()
    val barberShops by customerViewModel.barberShops.collectAsStateWithLifecycle()
    val screen by customerViewModel.screen.collectAsStateWithLifecycle()
    if (screen != "") {
        navController?.navigate(screen)
    }
    Scaffold(
        topBar = {
            TopAppBarComponent("HairBook")
        },
        bottomBar = { BottomAppBarComponent() },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TopAppBarComponent(text = "Search Shop")
                TextFieldComponent(
                    value = searchText,
                    placeholderText = "Search",
                    icon = Icons.Outlined.Search,
                    onValueChange = customerViewModel::onSearchTextChanged,
                )
                BarberShopList(
                    barberShops,
                    onBarberShopClick = customerViewModel::onBarberShopClicked
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