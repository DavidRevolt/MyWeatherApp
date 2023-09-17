package com.example.myweatherapp.ui.search

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions

import androidx.navigation.compose.composable


const val searchRoute = "search_route"
fun NavController.navigateToSearch(navOptions: NavOptions? = null) {
    this.navigate(searchRoute, navOptions)
}

fun NavGraphBuilder.searchScreen(
    onWeatherClick: (Int) -> Unit,
    onBackClick: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    composable(route = searchRoute) {
        SearchScreen(
            onWeatherClick = onWeatherClick,
            onBackClick = onBackClick,
            onShowSnackbar = onShowSnackbar
        )
    }

}