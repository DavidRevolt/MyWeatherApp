package com.example.myweatherapp.services.snackbarmanager

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState

interface SnackbarManager {

    val snackbarHostState: SnackbarHostState

    suspend fun showSnackbar(
        message: String,
        action: String? = null,
        duration: SnackbarDuration = SnackbarDuration.Short
    ): Boolean
}