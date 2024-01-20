package com.example.hairbookfront.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.example.hairbookfront.ui.Dimens
import com.example.hairbookfront.R

@Composable
fun TextFieldPassword(password: String, onValueChange: (String) -> Unit) {
    TextField(value = password,
        onValueChange = onValueChange ,
        placeholder = { Text("Password") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = PasswordVisualTransformation(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.mediumPadding1),
        trailingIcon = {
            Icon(
                painterResource(id = R.drawable.visibility_fill0_wght400_grad0_opsz24),
                contentDescription = null
            )
        }
    )
}