package com.example.myweatherapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * HiltAndroidApp triggers Hilt's code generation
 * */
@HiltAndroidApp
class WeatherApplication : Application() {
}