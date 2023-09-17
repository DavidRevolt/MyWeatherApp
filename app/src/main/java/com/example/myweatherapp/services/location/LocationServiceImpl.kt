package com.example.myweatherapp.services.location

import android.Manifest
import android.content.Context
import android.location.Location
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.LastLocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Tasks.await
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Get LastLocation from device GPS sensor
 * Make sure to ask 4 user permissions before launching the method
 * */

@Singleton
class LocationServiceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : LocationService {
    private var _locationRequest = LastLocationRequest.Builder().build()

    @RequiresPermission(
        anyOf = [
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION]
    )
    override suspend fun getLocation(): Result<Location?> =
        withContext(Dispatchers.IO) {
            // coroutine should be executed on a thread reserved for I/O operations.
            // using withContext(Dispatchers.IO) so we could use await
            val locationClient = LocationServices.getFusedLocationProviderClient(context)
            try {
                val result = await(locationClient.getLastLocation(_locationRequest))
                Result.success(result)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}





