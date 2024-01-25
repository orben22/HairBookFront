package com.example.hairbookfront.ui.shared.viewShop

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.hairbookfront.theme.HairBookFrontTheme
import com.example.hairbookfront.ui.Dimens
import com.example.hairbookfront.ui.common.BottomAppBarComponent
import com.example.hairbookfront.ui.common.TopAppBarComponent

@Composable
fun ViewShopScreen(
//    viewShopViewModel: ViewShopViewModel = hiltViewModel(),
    navController: NavHostController? = null
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
            BottomAppBarComponent(
                onClickFunctions = listOf(
                    { /* do something */ },
                    { /* do something */ }
                ), onClickFloating = { /* do something */ },
                numberOfIcons = 2,
                icons = listOf(
                    Icons.Filled.Check,
                    Icons.Filled.Edit
                ),
                textToIcon = listOf(
                    "Booking History",
                    "Write Review"
                ),
                floatingIcon = Icons.Filled.Add,
            )
        },
        topBar = {
            TopAppBarComponent(
                text = "Shop name"
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

                Text(text = "Description")
                Text(text = "Address")
                Text(text = "Phone Number")
                Text(text = "Opening days")

            }
        }
    )
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SignUpBarberScreenPreviewDark() {
    HairBookFrontTheme {
        ViewShopScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun SignUpBarberScreenPreviewLight() {
    HairBookFrontTheme {
        ViewShopScreen()
    }
}

