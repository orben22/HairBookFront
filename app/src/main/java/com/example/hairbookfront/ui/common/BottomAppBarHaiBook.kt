package com.example.hairbookfront.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BottomAppBarHaiBook(
    onClickFunctions: List<() -> Unit> = listOf({ }, { }),
    onClickFloating: () -> Unit = {},
    numberOfIcons: Int = 2,
    icons: List<ImageVector> = listOf(Icons.Filled.Star, Icons.Filled.Edit),
    textToIcon: List<String> = listOf("Booking History", "Write Review"),
    floatingIcon: ImageVector? = Icons.Filled.Add,
) {
    BottomAppBar(
        actions = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (i in 1..numberOfIcons) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.clickable { onClickFunctions[i - 1]() }
                        ) {
                            Icon(imageVector = icons[i - 1], contentDescription = null)
                            Text(
                                text = textToIcon[i - 1],
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
                FloatingActionButton(
                    onClick = onClickFloating,
                    modifier = Modifier.padding(end = 10.dp)
                ) {
                    if (floatingIcon != null) {
                        Icon(floatingIcon, null)
                    }
                }
            }
        },
    )
}


