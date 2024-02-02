package com.example.hairbookfront.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.hairbookfront.R

@Composable
fun DialogComponent(
    dialogTitle: String,
    dialogText: String,
    icon: Int = R.drawable.ic_launcher_round,
    numberOfConfirmButtons: Int = 2,
    textOfConfirmButtons: List<String> = listOf("Confirm", "Cancel"),
    confirmFunctions: List<() -> Unit>,
    dismissButton: Boolean = false,
    onDismissRequest: (() -> Unit)
) {
    AlertDialog(
        icon = {
            Image(painter = painterResource(id = icon), contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText, modifier = Modifier.fillMaxWidth())
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            Row {
                for (i in 1..numberOfConfirmButtons) {
                    TextButton(
                        onClick = {
                            confirmFunctions[i - 1]()
                        }
                    ) {
                        Text(text = textOfConfirmButtons[i - 1] ?: "Confirm")
                    }
                }
            }
        },
        dismissButton = {
            if (dismissButton) {
                TextButton(
                    onClick = {
                        onDismissRequest()
                    }
                ) {
                }
            }

        }
    )
}