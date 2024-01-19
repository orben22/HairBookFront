package com.example.hairbookfront.presentation.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.hairbookfront.presentation.Dimens

@Composable
fun AppTextField(value: String, placeholderText: String, icon: ImageVector?, onValueChange: (String) -> Unit ) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.mediumPadding1),
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = placeholderText) },
        trailingIcon = {
            if (icon != null) {
                Icon(
                    icon,
                    contentDescription = null
                )
            }
        }
    )
}
