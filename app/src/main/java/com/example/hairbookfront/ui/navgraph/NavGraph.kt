package com.example.hairbookfront.ui.navgraph

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.hairbookfront.ui.home.HomeScreen
import com.example.hairbookfront.ui.onboarding.OnBoardingScreen


@Composable
fun NavGraph(
    startDestination: String
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        navigation(
            route = Routes.AppStartNavigation.route,
            startDestination = Routes.OnBoardingScreen.route
        ) {
            composable(route = Routes.OnBoardingScreen.route) {
                OnBoardingScreen()
            }
        }
        navigation(
            route = Routes.NewsNavigation.route,
            startDestination = Routes.NewsNavigationScreen.route
        ) {
            composable(route = Routes.NewsNavigationScreen.route) {
                HomeScreen()
            }
        }
    }
}