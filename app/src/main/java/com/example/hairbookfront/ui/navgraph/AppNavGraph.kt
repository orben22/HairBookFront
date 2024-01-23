package com.example.hairbookfront.ui.navgraph

import com.example.hairbookfront.ui.barber.barberDetails.BarberDetailsViewModel
import WelcomePageScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.hairbookfront.ui.auth.signUpBarber.SignUpBarberScreen
import com.example.hairbookfront.ui.auth.signUpCustomer.SignUpCustomerScreen
import com.example.hairbookfront.ui.auth.welcome.WelcomeViewModel
import com.example.hairbookfront.ui.barber.barberDetails.BarberDetailsScreen
import com.example.hairbookfront.ui.customer.customerDetails.CustomerDetailsScreen
import com.example.hairbookfront.ui.customer.customerHome.CustomerHomeScreen
import com.example.hairbookfront.ui.customer.customerHome.CustomerHomeViewModel


@Composable
fun AppNavGraph(
    startDestination: String
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        navigation(
            route = Routes.AuthGraph.route,
            startDestination = Routes.WelcomeScreen.route
        ) {
            composable(route = Routes.WelcomeScreen.route) {
                val viewModel = it.sharedViewModel<WelcomeViewModel>(navController)
                WelcomePageScreen(viewModel, navController = navController)
            }
            composable(route = Routes.SignupCustomerScreen.route) {
                SignUpCustomerScreen(navController = navController)
            }
            composable(route = Routes.SignupBarberScreen.route) {
                SignUpBarberScreen(navController = navController)
            }
        }
        navigation(
            route = Routes.CustomerGraph.route,
            startDestination = Routes.CustomerHomeScreen.route
        ) {
            composable(route = Routes.CustomerHomeScreen.route) {
                val viewModel = it.sharedViewModel<CustomerHomeViewModel>(navController)
                CustomerHomeScreen(viewModel, navController = navController)
            }
            composable(route = Routes.CustomerDetailsScreen.route) {
                CustomerDetailsScreen()
            }
        }
        navigation(
            route = Routes.BarberGraph.route,
            startDestination = Routes.BarberDetailsScreen.route
        ) {
            composable(route = Routes.BarberDetailsScreen.route) {
                val viewModel = it.sharedViewModel<BarberDetailsViewModel>(navController)
                BarberDetailsScreen(viewModel)
            }
        }
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}