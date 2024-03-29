package com.example.hairbookfront.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.hairbookfront.ui.Dimens

@Composable
fun TextFieldComponent(
    value: String,
    placeholderText: String,
    icon: ImageVector?,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.smallPadding3),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xFFF3CC90),
            focusedContainerColor = Color(0xFFF3CC90),
            ),
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
        },
        isError = isError,
        keyboardOptions = keyboardOptions
    )
}
