package com.example.hairbookfront.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun BottomAppBarHaiBook(
    onClickFunctions: List<() -> Unit>,
    onClickFlaoting: () -> Unit,
    numberOfIcons: Int,
    icons: List<ImageVector>,
    textToIcon: List<String>,
    floatingIcon: ImageVector?,
    textToFloatingIcon: String

) {
    BottomAppBar(

        actions = {
            for (i in 1..numberOfIcons) {
                Column ()
                {
                    IconButton(onClick = onClickFunctions[i - 1]) {
                        Icon(imageVector = icons[i - 1], contentDescription = null)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text=textToIcon[i-1])
                }
            }
            Column (

            ){
                FloatingActionButton(
                    onClick = onClickFlaoting
                ) {
                    if (floatingIcon != null) {
                        Icon(floatingIcon, null)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = textToFloatingIcon)
            }
        },
    )
}