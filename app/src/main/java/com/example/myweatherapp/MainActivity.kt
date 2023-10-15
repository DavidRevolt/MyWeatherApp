package com.example.myweatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.example.myweatherapp.data.userdatarepository.UserDataRepository
import com.example.myweatherapp.services.networkmonitor.NetworkMonitorService
import com.example.myweatherapp.sync.SyncManager
import com.example.myweatherapp.ui.WeatherApp
import com.example.myweatherapp.ui.theme.MyWeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var networkMonitor: NetworkMonitorService

    @Inject
    lateinit var syncManager: SyncManager

    @Inject
    lateinit var userDataRepository: UserDataRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Edge2Edge
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            //Force English
            val locale = Locale("en")
            Locale.setDefault(locale)
            MyWeatherAppTheme {
                WeatherApp(syncManager, networkMonitor, userDataRepository)
            }
        }
    }
}
