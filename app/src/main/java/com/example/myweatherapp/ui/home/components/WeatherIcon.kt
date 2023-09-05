package com.example.myweatherapp.ui.home.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myweatherapp.R
import com.example.myweatherapp.utils.Constants

@Composable
fun WeatherIcon(icon: String,modifier: Modifier) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(Constants.IMG_URL + icon + ".png")
            .crossfade(true)
            .build(),
        contentDescription = stringResource(R.string.weatherIcon),
        modifier = modifier
    )
}