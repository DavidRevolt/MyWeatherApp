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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.myweatherapp.R
import com.example.myweatherapp.model.WeatherForecast
import kotlin.text.Typography.degree


@Composable
fun WeeklyForecast(modifier: Modifier = Modifier, forecastList: List<WeatherForecast>) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.5f)),
    ) {
        Column(
            modifier =Modifier.padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(modifier = Modifier.size(24.dp), contentAlignment = Alignment.Center) {
                        Icon(imageVector = Icons.Rounded.CalendarMonth , contentDescription ="",modifier = Modifier.size(20.dp) )
                    }
                    Text(
                        stringResource(R.string.forecast),
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .weight(3f),
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.weight(2f))
                    Text(
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .weight(1.5f),
                        text = stringResource(R.string.max_min),
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Row {
                    Divider()
                }

            forecastList.forEach { weather ->WeatherDetailRow(weather) }
        }
    }
}

@Composable
fun WeatherDetailRow(weather: WeatherForecast) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box() {
            WeatherIcon(weather.iconId, modifier = Modifier.size(24.dp))
        }

        Text(
            weather.date
                .split(",")[0],
            modifier = Modifier
                .padding(start = 5.dp)
                .weight(3f)
        )

        Text(
            weather.weatherDescription,
            modifier = Modifier
                .background(color = Color(0xFFFFC400).copy(0.8f), shape = CircleShape)
                .padding(2.dp)
                .weight(2f),
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center
        )

        Text(
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(start = 5.dp)
                .weight(1.5f),
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Blue.copy(alpha = 0.7f),
                        fontWeight = FontWeight.SemiBold
                    )
                ) {
                    append(weather.maxTemp + degree)
                }
                withStyle(
                    style = SpanStyle(
                        color = Color.Gray.copy(alpha = 0.8f)
                    )
                ) {
                    append(weather.minTemp + degree)
                }
            })
    }
}