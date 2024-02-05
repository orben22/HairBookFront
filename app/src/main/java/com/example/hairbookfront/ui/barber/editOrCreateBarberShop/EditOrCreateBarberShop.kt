package com.example.hairbookfront.ui.barber.editOrCreateBarberShop

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.hairbookfront.ui.Dimens
import com.example.hairbookfront.ui.common.TextFieldComponent
import com.example.hairbookfront.ui.common.TopAppBarComponent
import com.example.hairbookfront.ui.common.AddServiceDialog
import com.example.hairbookfront.ui.common.ServiceCard
import com.example.hairbookfront.util.Constants
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import timber.log.Timber
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditOrCreateBarberShopScreen(
    viewModel: EditOrCreateBarberShopViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    val screen by viewModel.screen.collectAsStateWithLifecycle()
    val lastScreen by viewModel.lastScreen.collectAsStateWithLifecycle()
    val mode by viewModel.mode.collectAsStateWithLifecycle()
    val barberShopName by viewModel.barberShopName.collectAsStateWithLifecycle()
    val barberShopError by viewModel.barberShopNameError.collectAsStateWithLifecycle()
    val description by viewModel.barberShopDescription.collectAsStateWithLifecycle()
    val descriptionError by viewModel.barberShopDescriptionError.collectAsStateWithLifecycle()
    val location by viewModel.barberShopAddress.collectAsStateWithLifecycle()
    val locationError by viewModel.barberShopAddressError.collectAsStateWithLifecycle()
    val barberShopPhoneNumber by viewModel.barberShopPhoneNumber.collectAsStateWithLifecycle()
    val barberShopPhoneNumberError by viewModel.barberShopPhoneNumberError.collectAsStateWithLifecycle()
    val daysOfWeek by viewModel.daysOfWeek.collectAsStateWithLifecycle()

    val services by viewModel.services.collectAsStateWithLifecycle()
    val showDialog by viewModel.showDialog.collectAsStateWithLifecycle()
    val shownClocks by viewModel.shownClocks.collectAsStateWithLifecycle()

    val serviceName by viewModel.serviceName.collectAsStateWithLifecycle()
    val serviceNameError by viewModel.serviceNameError.collectAsStateWithLifecycle()
    val servicePrice by viewModel.servicePrice.collectAsStateWithLifecycle()
    val servicePriceError by viewModel.servicePriceError.collectAsStateWithLifecycle()
    val serviceDuration by viewModel.serviceDuration.collectAsStateWithLifecycle()
    val serviceDurationError by viewModel.serviceDurationError.collectAsStateWithLifecycle()

    val days = viewModel.days
    val states = listOf(
        rememberSheetState(),
        rememberSheetState(),
        rememberSheetState(),
        rememberSheetState(),
        rememberSheetState(),
        rememberSheetState(),
        rememberSheetState()
    )
    val statesEnd = listOf(
        rememberSheetState(),
        rememberSheetState(),
        rememberSheetState(),
        rememberSheetState(),
        rememberSheetState(),
        rememberSheetState(),
        rememberSheetState()
    )

    for (i in daysOfWeek.indices) {
        if (daysOfWeek[i]) {
            ClockDialog(state = states[i], config = ClockConfig(
                is24HourFormat = true,
                defaultTime = LocalTime.MIN.plusHours(8),
            ), selection = ClockSelection.HoursMinutes { hours, minutes ->
                val min = if (minutes < 10) "0$minutes" else minutes
                viewModel.setStartTimeFunctions[i]("$hours:$min")
                statesEnd[i].show()
            })

            if (!shownClocks[i]) {
                states[i].show()
                viewModel.setShownClocks(i, true)
            }
            ClockDialog(state = statesEnd[i], config = ClockConfig(
                is24HourFormat = true,
                defaultTime = LocalTime.MIN.plusHours(9),
            ), selection = ClockSelection.HoursMinutes { hours, minutes ->
                val min = if (minutes < 10) "0$minutes" else minutes
                viewModel.setEndTimeFunctions[i]("$hours:$min")
            })
        }
    }
    LaunchedEffect(Unit) {
        viewModel.toastMessage.collect { message ->
            Toast.makeText(
                context,
                message,
                Toast.LENGTH_SHORT,
            ).show()
        }
    }
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
    Scaffold(topBar = {
        TopAppBarComponent(
            text = "$mode Barber Shop",
            dropDownMenu = false
        )
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = { viewModel.isValidInput() },
        ) {
            Icon(Icons.Filled.Add, "Floating action button.")
        }
    }, content = { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                TextFieldComponent(
                    value = barberShopName,
                    placeholderText = "BarberShop Name",
                    icon = Icons.Filled.Home,
                    onValueChange = { viewModel.setBarberShopName(it) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        capitalization = KeyboardCapitalization.Words,
                        keyboardType = KeyboardType.Text
                    ),
                    isError = barberShopError
                )
                TextFieldComponent(
                    value = description,
                    placeholderText = "Write a description",
                    icon = null,
                    onValueChange = { viewModel.setBarberShopDescription(it) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        capitalization = KeyboardCapitalization.Sentences,
                        keyboardType = KeyboardType.Text
                    ),
                    isError = descriptionError
                )
                TextFieldComponent(
                    value = location,
                    placeholderText = "Location",
                    icon = Icons.Outlined.LocationOn,
                    onValueChange = { viewModel.setBarberShopAddress(it) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        capitalization = KeyboardCapitalization.Words,
                        keyboardType = KeyboardType.Text
                    ),
                    isError = locationError
                )
                TextFieldComponent(
                    value = barberShopPhoneNumber,
                    placeholderText = "Phone Number",
                    icon = Icons.Outlined.Phone,
                    onValueChange = { viewModel.setPhoneNumber(it) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    isError = barberShopPhoneNumberError
                )
                Text(
                    modifier = Modifier.padding(
                        top = Dimens.smallPadding1, start = Dimens.smallPadding3
                    ),
                    text = "Please choose the days you work",
                    fontWeight = FontWeight.Bold,
                    fontSize = Dimens.fontLarge,
                    color = Color.Black
                )
            }
            item {
                ElevatedCard(
                    modifier = Modifier.padding(16.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    ),
                ) {
                    Column(
                    ) {
                        for (i in days.indices step 3) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                DayItem(
                                    day = days[i],
                                    isChecked = daysOfWeek[i],
                                    onCheckedChange = { isChecked ->
                                        viewModel.setDayOfWeek(i, isChecked)
                                        if (!isChecked) {
                                            viewModel.setShownClocks(i, false)
                                        }
                                    })
                                if (i + 1 < days.size) {
                                    DayItem(
                                        day = days[i + 1],
                                        isChecked = daysOfWeek[i + 1],
                                        onCheckedChange = { isChecked ->
                                            viewModel.setDayOfWeek(i + 1, isChecked)
                                            if (!isChecked) {
                                                viewModel.setShownClocks(i + 1, false)
                                            }
                                        })
                                }
                                if (i + 2 < days.size) {
                                    DayItem(
                                        day = days[i + 2],
                                        isChecked = daysOfWeek[i + 2],
                                        onCheckedChange = { isChecked ->
                                            viewModel.setDayOfWeek(i + 2, isChecked)
                                            if (!isChecked) {
                                                viewModel.setShownClocks(i + 2, false)
                                            }
                                        })
                                }
                            }
                        }
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp)
                ) {
                    for (day in days) {
                        if (daysOfWeek[days.indexOf(day)]) {
                            Text(
                                text = "$day: ${viewModel.timesOfWeek[days.indexOf(day)].first.collectAsState().value} - ${
                                    viewModel.timesOfWeek[days.indexOf(day)].second.collectAsState().value
                                }"
                            )
                        }
                    }
                }
            }

            item {
                Button(onClick = viewModel::onButtonClick) {
                    Text("Add Service")
                }

                AddServiceDialog(
                    showDialog = showDialog,
                    onAddService = {
                        viewModel.validateServiceInput()
                    },
                    barberShopId = "",
                    serviceName = serviceName,
                    onServiceNameChange = viewModel::setServiceName,
                    serviceNameError = serviceNameError,
                    servicePrice = servicePrice,
                    onServicePriceChange = viewModel::setServicePrice,
                    servicePriceError = servicePriceError,
                    serviceDuration = serviceDuration,
                    onServiceDurationChange = viewModel::setServiceDuration,
                    serviceDurationError = serviceDurationError,
                    onDismiss = viewModel::onDismiss
                )
                val editingService by viewModel.editingService.collectAsState()

                services.forEach { service ->
                    val isEditing = editingService?.serviceId == service.serviceId
                    Timber.d("isEditing: $isEditing")
                    ServiceCard(
                        service = service,
                        isSelected = false,
                        onServiceClick = {},
                        isEditable = isEditing,
                        serviceName = serviceName,
                        servicePrice = servicePrice,
                        serviceDuration = serviceDuration,
                        mode = Constants.BarberRole,
                        onEditClick = { viewModel.onEditClicked(it) },
                        onDeleteClick = {
                            service.serviceId?.let {
                                viewModel.onDeleteServiceClicked(
                                    it
                                )
                            }
                        },
                        onAcceptClick = { viewModel.onAcceptClicked(service) },
                        onDiscardClick = { },
                        onServiceNameChange = { viewModel.setServiceName(it) },
                        onPriceChange = { viewModel.setServicePrice(it) },
                        onDurationChange = { viewModel.setServiceDuration(it) }
                    )
                }
            }
        }
    })
}


@Composable
fun DayItem(day: String, isChecked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${day.first().uppercaseChar()}",
            fontWeight = FontWeight.Bold,
            fontSize = Dimens.fontSmall,
            color = Color.Black
        )
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(Color.Green)
        )
    }
}
