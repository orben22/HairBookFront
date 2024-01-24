package com.example.hairbookfront.ui.navgraph

sealed class Routes(val route: String) {


    // Destinations

    //Auth
    data object WelcomeScreen : Routes("welcomeScreen")

    data object SignupCustomerScreen : Routes("signupCustomerScreen")

    data object SignupBarberScreen : Routes("signupBarberScreen")

    //Customer
    data object CustomerHomeScreen : Routes("customerHomeScreen")

    data object CustomerDetailsScreen : Routes("customerDetailsScreen")

    data object CustomerBookingHistoryScreen : Routes("customerBookingHistoryScreen")

    data object CustomerReviewsHistoryScreen : Routes("customerReviewsHistoryScreen")


    //Barber
    data object BarberDetailsScreen : Routes("barberDetailsScreen")

    // Sub graphs
    data object AuthGraph : Routes("authGraph")

    data object CustomerGraph : Routes("customerGraph")

    data object BarberGraph : Routes("barberGraph")


}