package com.example.hairbookfront.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hairbookfront.ui.screens.HomeScreen

@Composable
fun AppNavigationGraph() {
    val navController = rememberNavController()

    //Coming from hilt
    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            HomeScreen()
        }
    }
}