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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.Navigation

//
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material3.DropdownMenuItem
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.ExposedDropdownMenuBox
//import androidx.compose.material3.ExposedDropdownMenuDefaults
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextField
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun DropDownMenuHairBook() {
//    var isExpanded by remember {
//        mutableStateOf(false)
//    }
//    val buttons by remember {
//        mutableStateOf("")
//    }
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.TopEnd
//    ) {
//        ExposedDropdownMenuBox(expanded = isExpanded, onExpandedChange = { isExpanded = it }) {
////            TextField(
////                value = buttons,
////                onValueChange = {},
////                readOnly = true,
////                trailingIcon = {
////                    ExposedDropdownMenuDefaults.TrailingIcon(
////                        expanded = isExpanded
////                    )
////                },
////                colors = ExposedDropdownMenuDefaults.textFieldColors(),
////                modifier = Modifier.menuAnchor()
////            )
//            ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
//                DropdownMenuItem(text = { Text(text = "Sign Out") },
//                    onClick = {
//                        isExpanded = false
//                        /**Add a function that signs out**/
//                        /**Add a function that signs out**/
//                    })
//            }
//            ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
//                DropdownMenuItem(text = { Text(text = "About") },
//                    onClick = {
//                        isExpanded = false
//                        /**Add a function that pops a about page**/
//                        /**Add a function that pops a about page**/
//                    })
//            }
//        }
//    }
//}


@Composable
//fun DropDownMenuHairBook(suggestions: List<String>)
fun DropDownMenuHairBook(
    //    suggestions: List<String>,
//    expanded: Boolean = false,
//    onClickMenu: (() -> Unit),
//    onDismissRequest: (() -> Unit),
//    listOfFunctions: List<(() -> Unit)>
) {
    var expanded by remember { mutableStateOf(false) }
    val suggestions = listOf("Kotlin", "Java", "Dart", "Python")
    var selectedText by remember { mutableStateOf("") }
    IconButton(onClick = {expanded=!expanded}) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = "Localized description"
        )
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
//        modifier = Modifier
//            .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
    ) {
        suggestions.forEach { label ->
            DropdownMenuItem(onClick = {
                selectedText = label
                expanded = false
            }, text = { Text("BlaBla") })
        }
    }
}