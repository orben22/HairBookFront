package com.example.hairbookfront.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun ButtonComponent(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector?
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .padding(bottom = 8.dp,start = 40.dp, end = 40.dp)
    ) {
        if (icon != null) {
            Icon(imageVector = icon, contentDescription = null)
            Spacer(modifier = Modifier.width(5.dp))
        }
        Text(text = text)
    }
}

@Composable
fun ClickableText(
    text: String,
    onClick: () -> Unit,
    color: Color = Color.Cyan,
    fontSize: Int = 16
) {
    Text(
        text = text,
        color = color,
        fontSize = fontSize.sp,
        modifier = Modifier.clickable(onClick = onClick)
    )
}
