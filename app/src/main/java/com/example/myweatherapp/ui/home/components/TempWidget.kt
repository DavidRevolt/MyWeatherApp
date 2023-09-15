package com.example.myweatherapp.ui.home.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myweatherapp.model.WeatherForecast
import com.example.myweatherapp.ui.designsystem.homeTempWidgetCurrentTempStyle
import com.example.myweatherapp.ui.designsystem.homeTempWidgetDateStyle
import com.example.myweatherapp.ui.designsystem.homeTempWidgetWeatherDescriptionStyle
import kotlin.text.Typography.degree


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TempWidget(modifier: Modifier = Modifier, forecast: WeatherForecast) {

    val fadeInTime = 1700
    val slideInTime = 1000
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
    ) {
        AnimatedContent(
            targetState = forecast, transitionSpec = {
                (scaleIn() + fadeIn(tween(fadeInTime, 0))).togetherWith(fadeOut(tween(0, 0)))
            }, label = ""
        )
        { animatedForecast ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = animatedForecast.currentTemp + degree,
                    style = homeTempWidgetCurrentTempStyle,
                    modifier = Modifier.animateEnterExit(
                        enter = slideInVertically(
                            animationSpec = tween(
                                slideInTime,
                                0,
                                FastOutSlowInEasing
                            )
                        )
                    ),
                )
                Text(
                    text = animatedForecast.weatherDescription,
                    style = homeTempWidgetWeatherDescriptionStyle,
                    modifier = Modifier.animateEnterExit(
                        enter = slideInHorizontally(
                            animationSpec = tween(slideInTime, 0, FastOutSlowInEasing)
                        )
                    )
                )
                Text(
                    text = animatedForecast.date,
                    style = homeTempWidgetDateStyle,
                    modifier = Modifier
                        .padding(0.dp)
                        .animateEnterExit(
                            enter = slideInHorizontally(
                                animationSpec = tween(
                                    slideInTime,
                                    0,
                                    FastOutSlowInEasing
                                ), initialOffsetX = { it / 2 })
                        ),
                )
            }
        }
    }
}

@Preview
@Composable
private fun WeatherWidgetPreview() {
    TempWidget(
        modifier = Modifier.fillMaxWidth(), forecast = WeatherForecast(
            currentTemp = "31",
            maxTemp = "40",
            minTemp = "28",
            weatherDescription = "Hot",
            date = "Friday, Sep 15,",
            atmosphericPressure = "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
        )
    )
}