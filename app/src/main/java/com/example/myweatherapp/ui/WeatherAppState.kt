package com.example.myweatherapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myweatherapp.Services.NetworkMonitor.NetworkMonitorService
import com.example.myweatherapp.data.userDataRepository.UserDataRepository
import com.example.myweatherapp.sync.SyncManager
import com.example.myweatherapp.ui.search.searchRoute
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@Composable
fun rememberWeatherAppState(
    syncManager: SyncManager,
    networkMonitor: NetworkMonitorService,
    userDataRepository: UserDataRepository,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): WeatherAppState {
    return remember(
        navController, networkMonitor, userDataRepository, syncManager
    ) {
        WeatherAppState(
            navController,
            coroutineScope,
            networkMonitor,
            userDataRepository,
            syncManager
        )
    }
}

class WeatherAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    networkMonitor: NetworkMonitorService,
    val userDataRepository: UserDataRepository,
    syncManager: SyncManager,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val shouldShowTransparentSystemBars
        @Composable get() = when (currentDestination?.route) {
            searchRoute -> false
            else -> true // eg: if we are in searchRoute we return null, which means we don't see the appBar
        }

    val isOffline = networkMonitor.isOnline()
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false,
        )

    val isSyncing = syncManager.isSyncing
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )


    fun navigateToHomeWithIndexToFocusOn(setWeatherIndexToFocusOn: Int) {
        coroutineScope.launch {
            userDataRepository.setWeatherIndexToFocusOn(setWeatherIndexToFocusOn)
            navController.popBackStack()
        }
    }


}






