package com.example.myweatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.example.myweatherapp.ui.WeatherAppState
import com.example.myweatherapp.ui.about.aboutScreen
import com.example.myweatherapp.ui.about.navigateToAbout
import com.example.myweatherapp.ui.home.homeRoute
import com.example.myweatherapp.ui.home.homeScreen
import com.example.myweatherapp.ui.search.navigateToSearch
import com.example.myweatherapp.ui.search.searchScreen

@Composable
fun WeatherNavigation(
    appState: WeatherAppState,
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = homeRoute,

    ) {
        homeScreen(
            onAboutClick = navController::navigateToAbout,
            onSearchClick = navController::navigateToSearch,
            onShowSnackbar = onShowSnackbar,
        )

        aboutScreen(onBackClick = navController::popBackStack)

        searchScreen(
            onWeatherClick = appState::navigateToHomeWithIndexToFocusOn,
            onBackClick = navController::popBackStack,
            onShowSnackbar = onShowSnackbar
        )
    }
}








