package com.example.myweatherapp.ui.designsystem

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

val BlackScrim = Color(0f, 0f, 0f, 0.3f) // 30% opaque black

val lightAppBarTextStyle =
    TextStyle(fontWeight = FontWeight.Light, fontSize = 20.sp, color = Color.White)
val lightAppBarIconTint = Color.White
val homePagerDotCurrentColor = Color.White
val homePagerDotColor = Color.LightGray
val homePullToRefreshTextStyle = TextStyle.Default.copy(color = Color.White, fontSize = 14.sp)


val homeTempWidgetCurrentTempStyle = TextStyle(
    textAlign = TextAlign.Center,
    fontWeight = FontWeight.Light,
    color = Color.White,
    fontSize = 140.sp,
)

val homeTempWidgetWeatherDescriptionStyle = TextStyle(
    fontStyle = FontStyle.Italic,
    textAlign = TextAlign.Center,
    fontWeight = FontWeight.Normal,
    color = Color.White,
    fontSize = 20.sp
)

val homeTempWidgetDateStyle = TextStyle(
    fontStyle = FontStyle.Italic,
    textAlign = TextAlign.Center,
    fontWeight = FontWeight.Normal,
    color = Color.White,
    fontSize = 20.sp
)

val homeCardColor = BlackScrim
val homeCardHeadersColor = Color.White.copy(alpha = 0.5f) //Used for Divider and Calendar Icon
val homeCardTitleStyle = TextStyle(
    color = Color.LightGray,
    fontWeight = FontWeight.Light,
    fontSize = 16.sp
)

val homeCardIconColorFilter = null // Null for no tint, ColorFilter.tint(Color.LightGray)

val homeCardDayStyle = TextStyle(
    color = Color.White,
    fontWeight = FontWeight.Light,
    fontSize = 16.sp
)

val homeCardWeatherDescriptionTextBackground =  Color.Black.copy(0.2f)
val homeCardWeatherDescriptionTextStyle = TextStyle(
    color = Color.White,
    fontWeight = FontWeight.Light,
    textAlign = TextAlign.Center,
    fontSize = 16.sp
)

val homeCardMaxTempStyle = SpanStyle(
    color = Color.White,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp
)

val homeCardMinTempStyle = SpanStyle(
    color = Color.LightGray,
    fontWeight = FontWeight.Light,
    fontSize = 16.sp
)

val homeInfoCardTitleStyle = SpanStyle(
    color = homeCardHeadersColor,
    fontWeight = FontWeight.SemiBold,
    fontSize = 14.sp
)

val homeInfoCardStartContentStyle = SpanStyle(
    color = Color.LightGray,
    fontWeight = FontWeight.Light,
    fontSize = 16.sp
)

val homeInfoCardEndContentStyle = SpanStyle(
    color = Color.White,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp
)

val homeInfoCardSecondaryEndContentStyle = SpanStyle(
    color = Color.LightGray,
    fontWeight = FontWeight.Light,
    fontSize = 12.sp
)

val aboutTextStyle = TextStyle(
    fontWeight = FontWeight.Light,
    color = Color.White
)