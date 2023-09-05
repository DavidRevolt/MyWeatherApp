package com.example.myweatherapp.ui.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

const val weatherIdArg = "weatherId"
const val homeRoute = "home_route/weatherId={weatherId}"

// Navigation with args currently not in use because using DataStore

fun NavController.navigateToHome(weatherId: Int?) {
    this.navigate("home_route/weatherId=${weatherId}")
}

fun NavGraphBuilder.homeScreen(
    onAboutClick: () -> Unit,
    onSearchClick: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,

    ) {
    composable(
        homeRoute,
        arguments = listOf(navArgument(weatherIdArg) {
            type = NavType.IntType
            defaultValue = 0
            //nullable = true  // if no args -> set query to null [not needed because defaultValue is set]
        }
        )
    ) {
        HomeScreen(
            onAboutClick = onAboutClick,
            onSearchClick = onSearchClick,
            onShowSnackbar = onShowSnackbar,
        )
    }

}