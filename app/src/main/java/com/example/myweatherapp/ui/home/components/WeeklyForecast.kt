package com.example.myweatherapp.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myweatherapp.R
import com.example.myweatherapp.model.WeatherForecast
import com.example.myweatherapp.ui.designsystem.homeCardColor
import com.example.myweatherapp.ui.designsystem.homeCardDayStyle
import com.example.myweatherapp.ui.designsystem.homeCardHeadersColor
import com.example.myweatherapp.ui.designsystem.homeCardIconColorFilter
import com.example.myweatherapp.ui.designsystem.homeCardMaxTempStyle
import com.example.myweatherapp.ui.designsystem.homeCardMinTempStyle
import com.example.myweatherapp.ui.designsystem.homeCardTitleStyle
import com.example.myweatherapp.ui.designsystem.homeCardWeatherDescriptionTextBackground
import com.example.myweatherapp.ui.designsystem.homeCardWeatherDescriptionTextStyle
import com.example.myweatherapp.utils.Constants
import kotlin.text.Typography.degree


@Composable
fun WeeklyForecast(modifier: Modifier = Modifier, forecastList: List<WeatherForecast>) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = homeCardColor)
    ) {
        Column(
            modifier = Modifier.padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(modifier = Modifier.size(24.dp), contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Rounded.CalendarMonth,
                        contentDescription = "",
                        modifier = Modifier.size(20.dp),
                        tint = homeCardHeadersColor
                    )
                }
                Text(
                    stringResource(R.string.forecast),
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .weight(3f),
                    style = homeCardTitleStyle
                )
                Spacer(modifier = Modifier.weight(2f))
                Text(
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .weight(1.5f),
                    text = stringResource(R.string.max_min),
                    style = homeCardTitleStyle
                )
            }
            Row {
                Divider(color = homeCardHeadersColor)
            }

            forecastList.forEach { weather -> WeatherDetailRow(weather) }
        }
    }
}

@Composable
fun WeatherDetailRow(weatherForecast: WeatherForecast) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box() {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(Constants.ICON_URL + weatherForecast.iconId + "@2x.png")
                    .crossfade(true)
                    .build(),

                contentDescription = stringResource(R.string.weatherIcon),
                modifier = Modifier.size(24.dp),
                colorFilter = homeCardIconColorFilter
            )
        }

        Text(
            weatherForecast.date
                .split(",")[0],
            modifier = Modifier
                .padding(start = 5.dp)
                .weight(3f),
            style = homeCardDayStyle
        )

        Text(
            weatherForecast.weatherDescription,
            modifier = Modifier
                .background(color = homeCardWeatherDescriptionTextBackground, shape = CircleShape)
                .padding(2.dp)
                .weight(2f),
            style = homeCardWeatherDescriptionTextStyle,
            maxLines = 1
        )

        Text(
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(start = 5.dp)
                .weight(1.5f),
            text = buildAnnotatedString {
                withStyle(
                    style = homeCardMaxTempStyle
                ) {
                    append(weatherForecast.maxTemp + degree)
                }
                withStyle(
                    style = homeCardMinTempStyle
                ) {
                    append(weatherForecast.minTemp + degree)
                }
            })
    }
}