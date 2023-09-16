package com.example.myweatherapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.example.myweatherapp.data.userDataRepository.UserDataRepository
import com.example.myweatherapp.services.NetworkMonitor.NetworkMonitorServiceImpl
import com.example.myweatherapp.sync.SyncManager
import com.example.myweatherapp.ui.WeatherApp
import com.example.myweatherapp.ui.theme.MyWeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    // JankStats, which is used to track jank throughout the app.
    @Inject
    lateinit var networkMonitor: NetworkMonitorServiceImpl

    @Inject
    lateinit var syncManager: SyncManager

    @Inject
    lateinit var userDataRepository: UserDataRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Used for making the status/nav bar transparent, Make sure to add lines in WeatherApp
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Here choose theme and using splash screen
        setContent {
            //Force English
            val locale = Locale("en")
            Locale.setDefault(locale)
            MyWeatherAppTheme {
                Log.d("", "Colors:")
                WeatherApp(syncManager, networkMonitor, userDataRepository)
            }
        }
    }
}




