package com.example.myweatherapp.Services.NetworkMonitor

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import javax.inject.Inject

class NetworkMonitorServiceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : NetworkMonitorService {

    // callbackFlow is a flow builder that lets you convert callback-based APIs into flows
    override fun isOnline(): Flow<Boolean> = callbackFlow {
        // Init connectivityManager
        val connectivityManager = context.getSystemService(ConnectivityManager::class.java)
        if (connectivityManager == null) {
            channel.trySend(false)
            channel.close() // Terminate the flow
            return@callbackFlow
        }

        // The Callback:
        // Holds sets of networks: WIFI, Cellular, etc... that holds internet connection.
        // If set is empty the flow return false, otherwise true
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            private val networks = mutableSetOf<Network>()

            override fun onAvailable(network: Network) {
                networks += network
                channel.trySend(true)
            }

            override fun onLost(network: Network) {
                networks -= network
                channel.trySend(networks.isNotEmpty())
            }
        }

        // Register for network updates with the given from above networkCallback
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)

        // Flow completes as soon as the code in the block completes. awaitClose should be used to keep the flow running.
        awaitClose {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        }
    }.conflate()
}

