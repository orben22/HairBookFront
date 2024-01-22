package com.example.hairbookfront.ui.navgraph

sealed class Routes(val route: String) {


    // Destinations
    object WelcomeScreen : Routes("welcomeScreen")

    object SignupCustomerScreen : Routes("signupCustomerScreen")

    object SignupBarberScreen : Routes("signupBarberScreen")

    object CustomerHomeScreen : Routes("customerHomeScreen")

    object CustomerDetailsScreen : Routes("customerDetailsScreen")

    object NewsNavigationScreen : Routes("newsNavigationScreen")

    // Sub graphs
    object AuthGraph : Routes("authGraph")

    object CustomerGraph : Routes("customerGraph")

    object BarberGraph : Routes("barberGraph")

    object NewsNavigation : Routes("newsNavigation")


}