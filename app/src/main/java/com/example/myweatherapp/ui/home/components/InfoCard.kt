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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//Title example: [Day, Temp, max/min]
//content example: [Pressure, 32, m/bar]
@Composable
fun InfoCard(
    modifier: Modifier = Modifier,
    title: Triple<String?, String?, String?>? =  null,
    content: List<Triple<String, String, String?>> = emptyList(),
    useDivider: Boolean = true,
    titleStyle: SpanStyle = SpanStyle(
        color = Color.Black,
        fontWeight = FontWeight.SemiBold
    ),
    startContentStyle: SpanStyle = SpanStyle(
        color = Color.Black,
        fontSize = 15.sp
    ),
    endContentStyle: SpanStyle = SpanStyle(
        color = Color.Black.copy(alpha = 0.7f),
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp
    ),
    secondaryEndContentStyle: SpanStyle = SpanStyle(
        color = Color.Gray.copy(alpha = 0.7f),
        fontWeight = FontWeight.Bold,
        fontSize = 10.sp
    ),
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.5f)),
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
                Divider()
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
                    if(useDivider) Divider()
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
            Text(
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
            Text(
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

