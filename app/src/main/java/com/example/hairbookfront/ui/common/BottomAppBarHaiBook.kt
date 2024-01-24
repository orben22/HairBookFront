package com.example.hairbookfront.ui.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun BottomAppBarHaiBook(
    onClick1: () -> Unit,
    onClick2: () -> Unit,
    flaotingOnClick: () -> Unit,
    icon1: ImageVector?,
    icon2: ImageVector?,
    floatingIcon: ImageVector?

) {
    BottomAppBar(
        actions = {
            IconButton(onClick = onClick1) {
                if (icon1 != null) {
                    Icon(imageVector = icon1, contentDescription = null)
                }
            }
            IconButton(onClick = onClick2) {
                if (icon2 != null) {
                    Icon(
                        imageVector = icon2, contentDescription = null
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = flaotingOnClick,
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                if (floatingIcon != null) {
                    Icon(floatingIcon, null)
                }
            }
        }
    )
}