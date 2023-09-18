package com.example.myweatherapp.ui.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val homeRoute = "home_route"

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(homeRoute,navOptions)
}

fun NavGraphBuilder.homeScreen(
    onAboutClick: () -> Unit,
    onSearchClick: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,

    ) {
    composable(homeRoute) {
        HomeScreen(
            onAboutClick = onAboutClick,
            onSearchClick = onSearchClick,
            onShowSnackbar = onShowSnackbar,
        )
    }
}