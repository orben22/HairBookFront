package com.example.hairbookfront.ui.common

import android.os.Bundle
import android.provider.ContactsContract.Contacts.AggregationSuggestions
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.Navigation

@Composable
fun DropDownMenuHairBook(
    suggestions: List<String> = listOf("Sign Out", "About"),
    expanded: Boolean = false,
    expandFunction: (() -> Unit) = {},
    onDismissRequest: (() -> Unit) = {},
    onClickMenus: List<(() -> Unit)> = listOf({}, {})
) {
    IconButton(onClick = { expandFunction() }) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = "Localized description"
        )
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onDismissRequest() },
    ) {
        suggestions.forEach { label ->
            DropdownMenuItem(onClick = {
                expandFunction()
                onClickMenus[suggestions.indexOf(label)]()
            }, text = { Text(text = label) })
        }
    }
}


