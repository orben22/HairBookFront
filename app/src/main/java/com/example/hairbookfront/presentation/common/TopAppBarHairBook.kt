package com.example.hairbookfront.presentation.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarHairBook(text: String){
    TopAppBar(
        title = {
            Text(text = text)
        },
        navigationIcon = {
            IconButton(onClick = { /* Handle navigation back */ }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        },
        actions = {
            // Add any additional actions if needed
        }
    )
}