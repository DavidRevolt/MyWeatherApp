package com.example.myweatherapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myweatherapp.R
import com.example.myweatherapp.data.userDataRepository.UserDataRepository
import com.example.myweatherapp.navigation.WeatherNavigation
import com.example.myweatherapp.services.NetworkMonitor.NetworkMonitorService
import com.example.myweatherapp.sync.SyncManager
import com.example.myweatherapp.ui.designsystem.RequestNotificationPermission
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WeatherApp(
    syncManager: SyncManager,
    networkMonitor: NetworkMonitorService,
    userDataRepository: UserDataRepository,
    appState: WeatherAppState = rememberWeatherAppState(
        syncManager,
        networkMonitor,
        userDataRepository
    )
) {

    RequestNotificationPermission()
    syncManager.requestSync()
    val isOffline by appState.isOffline.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    WeatherBackground(
        backgroundImage = painterResource(id = R.drawable.background)
    ) {

        val notConnectedMessage = stringResource(R.string.not_connected)
        LaunchedEffect(isOffline) {
            if (isOffline) {
                snackbarHostState.showSnackbar(
                    message = notConnectedMessage,
                    duration = SnackbarDuration.Indefinite,
                )
            }
        }
        val systemUiController = rememberSystemUiController()
        systemUiController.systemBarsDarkContentEnabled = !appState.shouldShowTransparentSystemBars

        Scaffold(
            containerColor = Color.Transparent,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            snackbarHost = {
                SnackbarHost(
                    snackbarHostState,
                    modifier = Modifier.safeDrawingPadding()
                )
            },
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                //if there global appbar it can show in another column element inside this
                WeatherNavigation(
                    appState = appState,
                    onShowSnackbar = { message, action ->
                        snackbarHostState.showSnackbar(
                            message = message,
                            actionLabel = action,
                            duration = SnackbarDuration.Short,
                        ) == SnackbarResult.ActionPerformed
                    })
            }
        }
    }
}


@Composable
private fun WeatherBackground(
    backgroundImage: Painter? = null,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        //Set Background Image
        if (backgroundImage != null) {
            Image(
                painter = backgroundImage,
                modifier = Modifier.fillMaxSize(),
                contentDescription = stringResource(R.string.app_background_image),
                contentScale = ContentScale.FillBounds
            )
        }
        content()
    }
}
