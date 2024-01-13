package com.example.hairbookfront.presentation.navgraph

sealed class Routes(val route: String) {


    // Destinations
    object OnBoardingScreen : Routes("onBoardingScreen")
    object HomeScreen : Routes("homeScreen")
    object SearchScreen : Routes("searchScreen")
    object BookMarkScreen : Routes("bookMarkScreen")
    object DetailScreen : Routes("detailScreen")
    object NewsNavigationScreen : Routes("newsNavigationScreen")

    // Sub graphs
    object AppStartNavigation : Routes("appStartNavigation")
    object NewsNavigation : Routes("newsNavigation")


}