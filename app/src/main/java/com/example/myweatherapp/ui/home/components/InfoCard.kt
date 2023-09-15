package com.example.myweatherapp.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.myweatherapp.ui.designsystem.homeCardColor
import com.example.myweatherapp.ui.designsystem.homeCardHeadersColor
import com.example.myweatherapp.ui.designsystem.homeInfoCardEndContentStyle
import com.example.myweatherapp.ui.designsystem.homeInfoCardSecondaryEndContentStyle
import com.example.myweatherapp.ui.designsystem.homeInfoCardStartContentStyle
import com.example.myweatherapp.ui.designsystem.homeInfoCardTitleStyle

//Title example: [Day, Temp, max/min]
//content example: [Pressure, 32, m/bar]
@Composable
fun InfoCard(
    modifier: Modifier = Modifier,
    title: Triple<String?, String?, String?>? =  null,
    content: List<Triple<String, String, String?>> = emptyList(),
    useDivider: Boolean = true,
    titleStyle: SpanStyle = homeInfoCardTitleStyle,
    startContentStyle: SpanStyle = homeInfoCardStartContentStyle,
    endContentStyle: SpanStyle = homeInfoCardEndContentStyle,
    secondaryEndContentStyle: SpanStyle = homeInfoCardSecondaryEndContentStyle,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = homeCardColor),
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (title != null) {
                InfoCardRow(
                    title.first,
                    title.second,
                    title.third,
                    titleStyle,
                    titleStyle,
                    secondaryEndContentStyle
                )
                Divider(color = homeCardHeadersColor)
            }

            //Set Content:
            if (content.isNotEmpty()) {
                val contentIterator = content.iterator()
                var data = contentIterator.next()
                InfoCardRow(
                    data.first,
                    data.second,
                    data.third,
                    startContentStyle,
                    endContentStyle,
                    secondaryEndContentStyle
                )
                while (contentIterator.hasNext()) {
                    data = contentIterator.next()
                    if(useDivider) Divider(color = homeCardHeadersColor)
                    InfoCardRow(
                        data.first,
                        data.second,
                        data.third,
                        startContentStyle,
                        endContentStyle,
                        secondaryEndContentStyle
                    )
                }
            }


        }
    }
}

@Composable
fun InfoCardRow(
    startText: String?,
    endText: String?,
    secondaryEndText: String?,
    startTextStyle: SpanStyle = SpanStyle(),
    endTextStyle: SpanStyle = SpanStyle(),
    secondaryEndTextStyle: SpanStyle = SpanStyle()
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween //How To Spread The Text In The Row
    ) {
        startText?.let {
            Text(maxLines = 1,
                modifier = Modifier.padding(start = 5.dp),
                text = buildAnnotatedString {
                    withStyle(
                        style = startTextStyle
                    ) {
                        append(startText)
                    }
                },
            )
        }
        endText?.let {
            Text(maxLines = 1,
                modifier = Modifier
                    .padding(end = 5.dp),
                text = buildAnnotatedString {
                    withStyle(
                        style = endTextStyle
                    ) {
                        append(endText)
                    }
                    secondaryEndText?.let {
                        withStyle(
                            style = secondaryEndTextStyle
                        ) {
                            append(secondaryEndText)
                        }
                    }
                })
        }
    }
}

