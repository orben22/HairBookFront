package com.example.hairbookfront.ui.shared

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.example.hairbookfront.theme.HairBookFrontTheme
import com.example.hairbookfront.ui.Dimens
import com.example.hairbookfront.ui.common.TopAppBarHairBook

@Composable
fun ViewShop(
//    viewShopViewModel: ViewShopViewModel = hiltViewModel(),
//    navController: NavHostController?
) {
//    val barberShopName by viewShopViewModel.barberShopName.collectAsStateWithLifecycle()
//    val openDays by viewShopViewModel.openDays.collectAsStateWithLifecycle()
//    val sundayHours by viewShopViewModel.sundayHours.collectAsStateWithLifecycle()
//    val mondayHours by viewShopViewModel.mondayHours.collectAsStateWithLifecycle()
//    val tuesdayHours by viewShopViewModel.tuesdayHours.collectAsStateWithLifecycle()
//    val wednesdayHours by viewShopViewModel.wednesdayHours.collectAsStateWithLifecycle()
//    val thursdayHours by viewShopViewModel.thursdayHours.collectAsStateWithLifecycle()
//    val fridayHours by viewShopViewModel.fridayHours.collectAsStateWithLifecycle()
//    val saturdayHours by viewShopViewModel.saturdayHours.collectAsStateWithLifecycle()
    Scaffold(
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(Icons.Filled.Star, contentDescription = "Localized description")
                    }
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            Icons.Filled.Edit,
                            contentDescription = "Localized description",
                        )
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { /* do something */ },
                        containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                    ) {
                        Icon(Icons.Filled.Add, "Localized description")
                    }
                }
            )},


//            BottomAppBar(
//                actions = {
//                    Row (
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(),
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.CenterVertically
//                    ){
//                        IconButton(onClick = { /* do something */ }) {
//                            Row {
//
//                                Icon(
//                                    Icons.Filled.Check,
//                                    contentDescription = "Localized description",
//                                )
//                                Text(text = "Book Haircut")
//                            }
//                        }
//                        IconButton(onClick = { /* do something */ }) {
//                            Icon(
//                                Icons.Filled.Edit,
//                                contentDescription = "Localized description",
//                            )
//                        }
//                    }
//                }
//            )
        topBar = {
            TopAppBarHairBook(
                text = "Hello"
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(
                        start = Dimens.distanceFromLeft,
                        end = Dimens.distanceFromLeft,
                        top = Dimens.distanceFromBottom,
                        bottom = Dimens.distanceFromBottom
                    )
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {

            }
        }
    )
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SignUpBarberScreenPreviewDark() {
    HairBookFrontTheme {
        ViewShop()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun SignUpBarberScreenPreviewLight() {
    HairBookFrontTheme {
        ViewShop()
    }
}

