package com.example.hairbookfront.ui.navgraph

/**
 * Sealed class for the routes in the app.
 *
 * @property route The route for the destination.
 */
sealed class Routes(val route: String) {

    // Destinations
    //Auth
    data object WelcomeScreen : Routes("welcomeScreen")

    data object SignupCustomerScreen : Routes("signupCustomerScreen")

    data object SignupBarberScreen : Routes("signupBarberScreen")

    //Customer
    data object CustomerHomeScreen : Routes("customerHomeScreen")

    data object CustomerDetailsScreen : Routes("customerDetailsScreen")


    data object CustomerReviewsHistoryScreen : Routes("customerReviewsHistoryScreen")


    //Barber
    data object BarberDetailsScreen : Routes("barberDetailsScreen")

    data object EditOrCreateBarberShopScreen : Routes("editOrCreateBarberShopScreen")

    //Shared
    data object EditOrCreateBookingScreen : Routes("editOrCreateBookingScreen")

    data object EditOrCreateReviewScreen : Routes("editOrCreateReviewScreen")

    data object ViewShopScreen : Routes("viewShopScreen")

    data object ReadReviewScreen : Routes("readReviewScreen")

    data object MyBookingsScreen : Routes("myBookingsScreen")


    // Sub graphs
    data object AuthGraph : Routes("authGraph")

    data object CustomerGraph : Routes("customerGraph")

    data object BarberGraph : Routes("barberGraph")


}