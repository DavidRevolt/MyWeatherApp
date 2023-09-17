package com.example.myweatherapp.ui.about

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val aboutRoute = "about_route"
fun NavController.navigateToAbout(navOptions: NavOptions? = null) {
    this.navigate(aboutRoute, navOptions)
}

fun NavGraphBuilder.aboutScreen(onBackClick: () -> Unit) {
    composable(route = aboutRoute) {
        AboutScreen(onBackClick = onBackClick)
    }

}

