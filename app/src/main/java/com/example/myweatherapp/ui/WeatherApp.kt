package com.example.myweatherapp.ui

import android.app.Activity
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myweatherapp.R
import com.example.myweatherapp.Services.NetworkMonitor.NetworkMonitorService
import com.example.myweatherapp.Services.notifications.RequestNotificationPermission
import com.example.myweatherapp.data.userDataRepository.UserDataRepository
import com.example.myweatherapp.navigation.WeatherNavigation
import com.example.myweatherapp.sync.SyncManager


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
        backgroundImage = painterResource(id = R.drawable.wallpaper6)
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

        val view = LocalView.current
        val window = (view.context as Activity).window
        val navBarColor: Color by animateColorAsState(
            if (appState.shouldShowTransparentSystemBars) Color.Transparent else Color.White,
            animationSpec = tween(200, easing = LinearEasing)
        )

        window.navigationBarColor = navBarColor.toArgb()

        Scaffold(
            containerColor = Color.Transparent,
            contentWindowInsets = WindowInsets.navigationBars,
            snackbarHost = { SnackbarHost(snackbarHostState) },
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
                painter = painterResource(id = R.drawable.wallpaper6),
                modifier = Modifier.fillMaxSize(),
                contentDescription = stringResource(R.string.app_background_image),
                contentScale = ContentScale.FillBounds
            )
        }
        content()
    }
}

