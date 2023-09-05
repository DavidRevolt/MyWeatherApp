package com.example.myweatherapp.ui.components.example

import android.annotation.SuppressLint
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import java.util.concurrent.TimeUnit

/*
* This method gets user current location and keeps updating while moving,
* this isn't one time action because we are using LocationRequest and requestLocationUpdates:
* get the locations of the device at regular intervals
* When user composable no longer in use -> Recording is Stopped!
*
* implementation "com.google.accompanist:accompanist-permissions:0.33.0-alpha"
* implementation 'com.google.android.gms:play-services-location:21.0.1'
*
* The accuracy of the location is determined by the providers, the location permissions you've requested, and the options you set in the location request.
* https://github.com/android/platform-samples/blob/main/samples/location/src/main/java/com/example/platform/location/locationupdates/LocationUpdatesScreen.kt
* https://proandroiddev.com/getting-user-location-in-android-the-jetpack-compose-way-ebd35dabab46
* */


//first Entry Point -> Check if we need permissions
@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("MissingPermission")
@Composable
fun GetLocationsUpdates() {
    val locationPermissionsState = rememberMultiplePermissionsState(
        listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
        )
    )

    if (locationPermissionsState.allPermissionsGranted) {
        LocationUpdatesContent(usePreciseLocation = true)
        Log.d("LocationsLog", "Using Precise Location")
    } else {
        Column {
            val allPermissionsRevoked =
                locationPermissionsState.permissions.size ==
                        locationPermissionsState.revokedPermissions.size

            if (!allPermissionsRevoked) {
                // If not all the permissions are revoked, it's because the user accepted the COARSE
                // location permission, but not the FINE one.
                LocationUpdatesContent(usePreciseLocation = true)
                Log.d("MyLog", "Using Coarse Location")
            } else if (locationPermissionsState.shouldShowRationale) {
                // Both location permissions have been previously denied
                Log.d("LocationsLog", "Permissions denied before, not asking you again!")
            } else {
                // First time the user sees this feature or the user doesn't want to be asked again
                Log.d("LocationsLog", "First Time Asking For Permissions")
                LaunchedEffect(Unit) {
                    locationPermissionsState.launchMultiplePermissionRequest()
                }
            }
        }
    }
}


//second Entry Point -> Check UI and what to do with list of locations
@RequiresPermission(
    anyOf = [
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_FINE_LOCATION,
    ]
)
@Composable
private fun LocationUpdatesContent(usePreciseLocation: Boolean) {
    // The location request that defines the location updates
    var locationRequest by remember { mutableStateOf<LocationRequest?>(null) }

    // Keeps track of received location updates as text
    var locationUpdates by remember { mutableStateOf("") }

    // Only register the location updates effect when we have a request
    if (locationRequest != null) {
        LocationUpdatesEffect(locationRequest!!) { locations ->
            // For each result update the text and Latitude and Longitude data class

            //This option returns the locations computed, ordered from oldest to newest.
            for (location in locations.locations) {
                locationUpdates = "${System.currentTimeMillis()}:\n" +
                        "- @lat: ${location.latitude}\n" +
                        "- @lng: ${location.longitude}\n" +
                        "- Accuracy: ${location.accuracy}\n\n" +
                        //locationUpdates
                Log.d(
                    "LocationsLog", "${System.currentTimeMillis()}:\n" +
                            "- @lat: ${location.latitude}\n" +
                            "- @lng: ${location.longitude}\n" +
                            "- Accuracy: ${location.accuracy}\n\n" + locationUpdates
                )
            }
        }
    }

    //
    //Screen UI
    //
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item {
            // Toggle to start and stop location updates
            // before asking for periodic location updates,
            // it's good practice to fetch the current location
            // or get the last known location
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Enable location updates")
                Spacer(modifier = Modifier.padding(8.dp))
                Switch(
                    checked = locationRequest != null, //Switch Locations Recording Requests on anf off
                    onCheckedChange = { checked ->
                        locationRequest = if (checked) {
                            // Define the accuracy based on your needs and granted permissions
                            val priority = if (usePreciseLocation) {
                                Priority.PRIORITY_HIGH_ACCURACY
                            } else {
                                Priority.PRIORITY_BALANCED_POWER_ACCURACY
                            }
                            LocationRequest.Builder(priority, TimeUnit.SECONDS.toMillis(3))
                                .build()
                        } else {
                            null
                        }
                    },
                )
            }
        }
        item {
            Text(text = locationUpdates)
        }
    }
}


@RequiresPermission(
    anyOf = [
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_FINE_LOCATION,
    ]
)
@Composable
fun LocationUpdatesEffect(
    locationRequest: LocationRequest,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onUpdate: (result: LocationResult) -> Unit,
) {
    val context = LocalContext.current
    val currentOnUpdate by rememberUpdatedState(newValue = onUpdate)


    // Whenever on of these parameters changes, dispose and restart the effect.
    DisposableEffect(locationRequest, lifecycleOwner) {
        Log.d("LocationsLog","Recording Locations ACTIVATED!")
        val locationClient = LocationServices.getFusedLocationProviderClient(context)
        val locationCallback: LocationCallback = object : LocationCallback() {
            @SuppressLint("MissingPermission")
            override fun onLocationResult(result: LocationResult) {
                currentOnUpdate(result)
            }
        }

        //We Need observer because this method keeps running in composition
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                Log.d("LocationsLog","Recording Locations ACTIVATED!")
                //here we are binding the FusedClient, LocationRequest and the locationCallback method
                //we need to use locationRequest AND NOT locationRequest.priority!!!! unlike getting CurrentLocation
                locationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
            } else if (event == Lifecycle.Event.ON_STOP) {
                //When leaving parent composition
                Log.d("LocationsLog","Recording Locations STOPPED2!")
                locationClient.removeLocationUpdates(locationCallback)
            }
        }

        // Add the observer to the lifecycle of parent composable
        lifecycleOwner.lifecycle.addObserver(observer)

        // When the effect leaves the Composition or when changing locationRequest, remove the observer
        // This also make sure that if we try to get locations again, the former request will be canceled!
        onDispose {
            Log.d("LocationsLog","Recording Locations STOPPED!")
            locationClient.removeLocationUpdates(locationCallback)
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}
