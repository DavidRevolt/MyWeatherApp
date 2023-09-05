package com.example.myweatherapp.Services.notifications

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestNotificationPermission() {
    val postNotificationPermission =
        rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)

    // if user first time seeing this dialog or never pressed denied or accept
    if (!postNotificationPermission.status.isGranted && !postNotificationPermission.status.shouldShowRationale) {
        SideEffect {
            postNotificationPermission.launchPermissionRequest()
        }
    }
}