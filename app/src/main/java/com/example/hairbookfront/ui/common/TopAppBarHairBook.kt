package com.example.hairbookfront.ui.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarHairBook(
    text: String,
    dropDownMenu: Boolean = false,
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
                DropDownMenuHairBook(
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

