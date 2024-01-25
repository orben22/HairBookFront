package com.example.hairbookfront.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.hairbookfront.ui.Dimens
import com.example.hairbookfront.R

@Composable
fun TextFieldPasswordComponent(
    password: String,
    passwordVisibility: Boolean,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    onIconClicked: () -> Unit,
) {

    val icon = if (passwordVisibility) {
        R.drawable.visibility_off_fill0_wght400_grad0_opsz24
    } else {
        R.drawable.visibility_fill0_wght400_grad0_opsz24
    }
    TextField(
        value = password,
        onValueChange = onValueChange,
        placeholder = { Text("Password") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.mediumPadding1),
        trailingIcon = {
            IconButton(
                onClick = onIconClicked
            ) {
                Icon(
                    painterResource(id = icon),
                    contentDescription = if (passwordVisibility) "Hide Password" else "Show Password"
                )
            }
        },
        isError = isError,
    )
}