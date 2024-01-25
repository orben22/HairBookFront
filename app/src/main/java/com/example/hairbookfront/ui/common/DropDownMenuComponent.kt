package com.example.hairbookfront.ui.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*

@Composable
fun DropDownMenuComponent(
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


