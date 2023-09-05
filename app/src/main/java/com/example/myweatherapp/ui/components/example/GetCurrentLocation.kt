package com.example.myweatherapp.ui.components.example

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.Task


// Getting one time user current location


// To build req:
// CurrentLocationRequest.Builder().setPriority(Priority.PRIORITY_HIGH_ACCURACY).setDurationMillis(600).build()
//
@SuppressLint("MissingPermission")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun GetCurrentLocation(currentLocationRequest: CurrentLocationRequest, onTask: (task: Task<Location>) -> Unit) {
    val context = LocalContext.current
    val locationPermissionsState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
    )

    val allPermissionsRevoked =
        locationPermissionsState.permissions.size ==
                locationPermissionsState.revokedPermissions.size

    if (!allPermissionsRevoked) {
        CurrentLocationContent(locationRequest = currentLocationRequest, onTask = onTask)
    } else if (locationPermissionsState.shouldShowRationale) {
        // Both location permissions have been previously denied
        Log.d("LocationsLog", "Permissions denied before, not asking you again!")
        Toast.makeText(context,"Location permissions denied",Toast.LENGTH_SHORT).show()
    }
    else{
        Log.d("LocationsLog", "Gonna Ask For Permissions!")
        SideEffect {
            locationPermissionsState.launchMultiplePermissionRequest()
        }
    }
}


// Requesting GPS Sensor Location, Request should be dismissed if we leave the screen or another request comes
@RequiresPermission(
    anyOf = [
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
    ]
)
@Composable
private fun CurrentLocationContent(
    locationRequest: CurrentLocationRequest,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onTask: (task: Task<Location>) -> Unit
) {
    val context = LocalContext.current
    val locationClient = LocationServices.getFusedLocationProviderClient(context)
    val cancellationToken = CancellationTokenSource()

    DisposableEffect(key1 = locationClient, key2 = lifecycleOwner ){
        // Need observer because this method keeps running in composition and we want to cancel req if we leave parent composition
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                Log.d("LocationsLog","Recording Locations ACTIVATED!")
                //we need to use locationRequest.priority AND NOT locationRequest!!!! unlike getting LocationUpdates
                onTask(locationClient.getCurrentLocation(locationRequest.priority, cancellationToken.token))

            } else if (event == Lifecycle.Event.ON_STOP) {
                //When leaving parent composition/Screen
                Log.d("LocationsLog", "Cancelling Request because leaving screen")
                cancellationToken.cancel()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose{
            // When the effect leaves the Composition or when changing locationRequest, remove the observer
            // This will make sure that if we try to get location again, the former request will be canceled!
            Log.d("LocationsLog", "Stopping Request")
            cancellationToken.cancel()
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

}