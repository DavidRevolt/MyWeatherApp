package com.example.myweatherapp.ui.home.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myweatherapp.R
import com.example.myweatherapp.utils.Constants

@Composable
fun WeatherIcon(icon: String,modifier: Modifier, colorFilter: ColorFilter? = null) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(Constants.ICON_URL + icon + "@2x.png")
            .crossfade(true)
            .build(),

        contentDescription = stringResource(R.string.weatherIcon),
        modifier = modifier,
        colorFilter = colorFilter
    )
}