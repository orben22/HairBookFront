package com.example.hairbookfront.presentation.CreateBarberShop

import android.content.res.Configuration
import android.service.autofill.OnClickAction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hairbookfront.ui.Dimens
import com.example.hairbookfront.ui.common.AppTextField
import com.example.hairbookfront.ui.common.BarberShopList
import com.example.hairbookfront.ui.common.SubmitButton
import com.example.hairbookfront.ui.common.TopAppBarHairBook
import com.example.hairbookfront.theme.HairBookFrontTheme
import com.example.hairbookfront.ui.common.AppTextField
import com.example.hairbookfront.ui.common.SubmitButton
import com.example.hairbookfront.ui.common.TopAppBarHairBook

@Composable
fun CreateBarberShop() {
    val barbershopname = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val location = remember { mutableStateOf("") }
    var sunday by remember { mutableStateOf(false) }
    var monday by remember { mutableStateOf(false) }
    var tuesday by remember { mutableStateOf(false) }
    var wednesday by remember { mutableStateOf(false) }
    var thursday by remember { mutableStateOf(false) }
    var friday by remember { mutableStateOf(false) }
    var saturday by remember { mutableStateOf(false) }
    val workingtime = remember { mutableStateOf("") }
    Surface(color = MaterialTheme.colorScheme.surface) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBarHairBook(text = "Create BarberShop")
            AppTextField(
                value = barbershopname.value,
                placeholderText = "BarberShop Name",
                icon = null,
                onValueChange = { barbershopname.value = it })
            AppTextField(
                value = description.value,
                placeholderText = "Write a description",
                icon = null,
                onValueChange = { description.value = it })
            AppTextField(
                value = location.value,
                placeholderText = "Location",
                icon = Icons.Outlined.LocationOn,
                onValueChange = { location.value = it })
            Text(
                modifier = Modifier.padding(
                    top = Dimens.smallPadding1,
                    start = Dimens.smallPadding3
                ),
                text = "Please choose the days you work",
                fontWeight = FontWeight.Bold,
                fontSize = Dimens.fontLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    modifier = Modifier.padding(
                        top = Dimens.smallPadding1,
                        start = Dimens.smallPadding3
                    ),
                    text = "Sunday",
                    fontWeight = FontWeight.Bold,
                    fontSize = Dimens.fontSmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    modifier = Modifier.padding(
                        top = Dimens.smallPadding1,
                        start = Dimens.smallPadding3
                    ),
                    text = "Monday",
                    fontWeight = FontWeight.Bold,
                    fontSize = Dimens.fontSmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    modifier = Modifier.padding(
                        top = Dimens.smallPadding1,
                        start = Dimens.smallPadding3
                    ),
                    text = "Tuesday",
                    fontWeight = FontWeight.Bold,
                    fontSize = Dimens.fontSmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Checkbox(

                    checked = sunday,
                    onCheckedChange = { sunday = it },
                    colors = CheckboxDefaults.colors(Color.Green)
                )
                Checkbox(
                    checked = monday,
                    onCheckedChange = { monday = it },
                    colors = CheckboxDefaults.colors(Color.Green)
                )
                Checkbox(
                    checked = tuesday,
                    onCheckedChange = { tuesday = it },
                    colors = CheckboxDefaults.colors(Color.Green)
                )
            }
            Row() {
                Text(
                    modifier = Modifier.padding(
                        top = Dimens.smallPadding1,
                        start = Dimens.smallPadding3
                    ),
                    text = "Wednesday",
                    fontWeight = FontWeight.Bold,
                    fontSize = Dimens.fontSmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    modifier = Modifier.padding(
                        top = Dimens.smallPadding1,
                        start = Dimens.smallPadding3
                    ),
                    text = "Thursday",
                    fontWeight = FontWeight.Bold,
                    fontSize = Dimens.fontSmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    modifier = Modifier.padding(
                        top = Dimens.smallPadding1,
                        start = Dimens.smallPadding3
                    ),
                    text = "Friday",
                    fontWeight = FontWeight.Bold,
                    fontSize = Dimens.fontSmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    modifier = Modifier.padding(
                        top = Dimens.smallPadding1,
                        start = Dimens.smallPadding3
                    ),
                    text = "Saturday",
                    fontWeight = FontWeight.Bold,
                    fontSize = Dimens.fontSmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            Row() {
                Checkbox(
                    checked = wednesday,
                    onCheckedChange = { wednesday = it },
                    colors = CheckboxDefaults.colors(Color.Green)
                )
                Checkbox(
                    checked = thursday,
                    onCheckedChange = { thursday = it },
                    colors = CheckboxDefaults.colors(Color.Green)
                )
                Checkbox(
                    checked = friday,
                    onCheckedChange = { friday = it },
                    colors = CheckboxDefaults.colors(Color.Green)
                )
                Checkbox(
                    checked = saturday,
                    onCheckedChange = { saturday = it },
                    colors = CheckboxDefaults.colors(Color.Green)
                )
            }
            SubmitButton()
        }

    }
}


@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun myReviewDark() {
    HairBookFrontTheme {


        CreateBarberShop()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun myReview() {
    HairBookFrontTheme {
        CreateBarberShop()
    }
}