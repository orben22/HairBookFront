package com.example.hairbookfront.ui.navgraph

import android.os.Build
import androidx.annotation.RequiresApi
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
import com.example.hairbookfront.ui.auth.welcome.WelcomePageScreen
import com.example.hairbookfront.ui.barber.barberDetails.BarberDetailsScreen
import com.example.hairbookfront.ui.barber.editOrCreateBarberShop.EditOrCreateBarberShopScreen
import com.example.hairbookfront.ui.customer.customerDetails.CustomerDetailsScreen
import com.example.hairbookfront.ui.customer.customerHome.CustomerHomeScreen
import com.example.hairbookfront.ui.customer.reviewsHistory.ReviewsHistoryScreen
import com.example.hairbookfront.ui.shared.myBookings.MyBookingsScreen
import com.example.hairbookfront.ui.shared.editOrCreateBooking.EditOrCreateBookingScreen
import com.example.hairbookfront.ui.shared.editOrCreateReview.EditOrCreateReviewScreen
import com.example.hairbookfront.ui.shared.readReviews.ReadReviewsScreen
import com.example.hairbookfront.ui.shared.viewShop.ViewShopScreen


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavGraph(
    startDestination: String
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        //Auth Sub graph
        navigation(
            route = Routes.AuthGraph.route,
            startDestination = Routes.WelcomeScreen.route
        ) {
            composable(route = Routes.WelcomeScreen.route) {
                WelcomePageScreen(navController = navController)
            }
            composable(route = Routes.SignupCustomerScreen.route) {
                SignUpCustomerScreen(navController = navController)
            }
            composable(route = Routes.SignupBarberScreen.route) {
                SignUpBarberScreen(navController = navController)
            }
        }

        //Customer Sub graph
        navigation(
            route = Routes.CustomerGraph.route,
            startDestination = Routes.CustomerHomeScreen.route
        ) {
            composable(route = Routes.CustomerHomeScreen.route) {
                CustomerHomeScreen(navController = navController)
            }
            composable(route = Routes.CustomerDetailsScreen.route) {
                CustomerDetailsScreen(navController = navController)
            }
            composable(route = Routes.CustomerReviewsHistoryScreen.route) {
                ReviewsHistoryScreen(navController = navController)
            }
            composable(route = Routes.EditOrCreateReviewScreen.route) {
                EditOrCreateReviewScreen(navController = navController)
            }
            composable(route = Routes.EditOrCreateBookingScreen.route) {
                EditOrCreateBookingScreen(navController = navController)
            }
            composable(route = Routes.ViewShopScreen.route) {
                ViewShopScreen(navController = navController)
            }
            composable(route = Routes.ReadReviewScreen.route) {
                ReadReviewsScreen(navController = navController)
            }
            composable(route = Routes.MyBookingsScreen.route) {
                MyBookingsScreen(navController = navController)
            }
        }

        //Barber Sub graph
        navigation(
            route = Routes.BarberGraph.route,
            startDestination = Routes.BarberDetailsScreen.route
        ) {
            composable(route = Routes.BarberDetailsScreen.route) {
                BarberDetailsScreen(navController = navController)
            }
            composable(route = Routes.EditOrCreateBarberShopScreen.route) {
                EditOrCreateBarberShopScreen(navController = navController)
            }
            composable(route = Routes.EditOrCreateReviewScreen.route) {
                EditOrCreateReviewScreen(navController = navController)
            }
            composable(route = Routes.EditOrCreateBookingScreen.route) {
                EditOrCreateBookingScreen(navController = navController)
            }
            composable(route = Routes.ViewShopScreen.route) {
                ViewShopScreen(navController = navController)
            }
            composable(route = Routes.ReadReviewScreen.route) {
                ReadReviewsScreen(navController = navController)
            }
            composable(route = Routes.MyBookingsScreen.route) {
                MyBookingsScreen(navController = navController)
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