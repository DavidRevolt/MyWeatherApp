package com.example.myweatherapp.ui.search.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myweatherapp.R
import com.example.myweatherapp.model.Weather
import kotlin.text.Typography.degree

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun WeatherPreviewCard(
    modifier: Modifier = Modifier,
    weatherList: List<Weather> = emptyList(),
    onRowClick: (Int) -> Unit ={},
    swipeToStartFunc: (Int) -> Unit ={},
    colors: List<Color> = listOf(MaterialTheme.colorScheme.primary),
) {
    LazyColumn(modifier = modifier) {

        itemsIndexed(items = weatherList, key = { _, item -> item.weatherId }) { index, item ->
            val currentItem by rememberUpdatedState(item) //Must Wrap item so its position and index will be updated if list change
            val dismissState = rememberDismissState(confirmValueChange = {
                if (it == DismissValue.DismissedToStart) {
                    swipeToStartFunc(currentItem.weatherId) //del from roomDB
                }
                true
            }, positionalThreshold = { distance -> distance * 0.25f })

            SwipeToDismiss(
                state = dismissState,
                directions = setOf(DismissDirection.EndToStart/*, DismissDirection.StartToEnd*/),
                background = { DismissBackground(dismissState) },
                dismissContent = {
                    PreviewCard(
                        weather = currentItem, onRowClick = { onRowClick(index) },
                        color = colors[index % colors.size]
                    )
                },
                modifier = Modifier
                    .animateItemPlacement(
                        tween(
                            durationMillis = 1500,
                            delayMillis = 50,
                            easing = FastOutSlowInEasing
                        )
                    )
            )
        }
    }
}

@Composable
fun PreviewCard(weather: Weather, onRowClick: () -> Unit, color: Color) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onRowClick.invoke() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = weather.weatherForecast[0].currentTemp + degree,
                modifier = Modifier
                    .weight(1f)
                    .padding(15.dp),
                style = TextStyle(
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Start,
                    color = Color.White,
                    fontSize = 60.sp,
                )
            )


            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = weather.city,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Text(textAlign = TextAlign.Center,
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            )
                        ) {
                            append(weather.weatherForecast[0].maxTemp + degree)

                        }
                        withStyle(
                            style = SpanStyle(
                                color = Color.White
                            )
                        ) {
                            append(weather.weatherForecast[0].minTemp + degree)
                        }
                    })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DismissBackground(dismissState: DismissState) {
    val haptic = LocalHapticFeedback.current
    val direction = dismissState.dismissDirection

    val color by animateColorAsState(
        targetValue = when (direction) {
            DismissDirection.StartToEnd -> Color.Transparent
            DismissDirection.EndToStart -> Color.Transparent
            null -> Color.Transparent
        }, label = ""

    )
    val alignment = when (direction) {
        DismissDirection.StartToEnd -> Alignment.CenterStart
        DismissDirection.EndToStart -> Alignment.CenterEnd
        else -> Alignment.CenterEnd
    }

    val icon = when (direction) {
        DismissDirection.StartToEnd -> Icons.Default.Done
        DismissDirection.EndToStart -> Icons.Default.Delete
        else -> Icons.Default.Delete
    }

    //How Much The Icon Gets Bigger
    val scale by animateFloatAsState(
        targetValue = if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f,
        finishedListener = {
            if (dismissState.targetValue == DismissValue.DismissedToStart) {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            }
        }, label = ""
    )

    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .background(color = color),
        contentAlignment = alignment
    ) {
        Icon(
            icon,
            contentDescription = stringResource(R.string.swipeToDismissIconDescription),
            modifier = Modifier
                .scale(scale)
        )
    }

}