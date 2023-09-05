# MyWeatherApp
**MyWeatherApp** is a Android app built entirely with Kotlin and Jetpack Compose. It
follows the [official architecture guidance](https://developer.android.com/jetpack/guide) as closely as possible.

## Features
*   Get 7 days forecast for your current location with a click.
*   Search and manage 7 days forecast for any location on the globe.

## Highlights
* Follows MVVM architecture.
* WorkManger executes sync job and keeping data up to date.
* DataStore for saving user preferred location.
* Dependency injection with Hilt.
* Using GPS sensors/Last known location for fetching local forecast.
* Monitoring network connections with ConnectivityManager.

  
## Libraries & Dependencies
- [Jetpack](https://developer.android.com/jetpack)
    - Compose - Define your UI programmatically with composable functions that describe its shape
- [Gradle Version Catalog](https://docs.gradle.org/7.4/userguide/platforms.html)
- [Accompanist](https://google.github.io/accompanist)
- [Retrofit2](https://github.com/square/retrofit)
- [Coil-Compose](https://coil-kt.github.io/coil/compose)
- [Timber](https://github.com/JakeWharton/timber)

## Installation
To install **MyWeatherApp**, follow these steps:
1. Clone or download the project code from the repository.
2. Open the project in Android Studio.
3. Insert your OpenWeather API key in: 
4. Build and run the app on an Android emulator or device.
