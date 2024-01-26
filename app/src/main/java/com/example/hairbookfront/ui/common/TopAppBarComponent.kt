package com.example.hairbookfront.ui.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarComponent(
    text: String,
    dropDownMenu: Boolean = true,
    onClickBackArrow: () -> Unit = { },
    suggestions: List<String> = listOf("Sign Out", "About"),
    expanded: Boolean = false,
    expandFunction: (() -> Unit) = {},
    onDismissRequest: (() -> Unit) = {},
    onClickMenus: List<(() -> Unit)> = listOf({}, {})
) {
    TopAppBar(
        title = {
            Text(text = text)
        },
        navigationIcon = {
            IconButton(onClick = { onClickBackArrow() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        },
        actions = {
            if (dropDownMenu) {
                DropDownMenuComponent(
                    suggestions = suggestions,
                    expanded = expanded,
                    expandFunction = expandFunction,
                    onDismissRequest = onDismissRequest,
                    onClickMenus = onClickMenus
                )
            }
        }
    )
}

