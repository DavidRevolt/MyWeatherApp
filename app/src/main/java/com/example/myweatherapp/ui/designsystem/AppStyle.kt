package com.example.myweatherapp.ui.designsystem

import androidx.compose.ui.graphics.Color
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
    fontSize = 140.sp
)

val homeTempWidgetWeatherDescriptionStyle = TextStyle(
    fontStyle = FontStyle.Italic,
    textAlign = TextAlign.Center,
    fontWeight = FontWeight.Light,
    color = Color.White,
    fontSize = 20.sp
)

val homeTempWidgetDateStyle = TextStyle(
    fontStyle = FontStyle.Italic,
    textAlign = TextAlign.Center,
    fontWeight = FontWeight.Light,
    color = Color.White,
    fontSize = 20.sp
)

val homeCardColor = BlackScrim
val homeCardHeadersColor = Color.White.copy(alpha = 0.5f)
val homeCardTitleStyle = TextStyle(
    color = homeCardHeadersColor,
    fontWeight = FontWeight.SemiBold
)

val homeCardIconColorFilter = null // Null for no tint, ColorFilter.tint(Color.LightGray)
val homeCardDayStyle = TextStyle(
    color = Color.LightGray,
    fontWeight = FontWeight.Normal
)

val homeCardMaxTempStyle = SpanStyle(
    color = Color.LightGray,
    fontWeight = FontWeight.Normal
)

val homeCardMinTempStyle = SpanStyle(
    color = Color.Gray,
    fontWeight = FontWeight.Thin
)

val homeInfoCardTitleStyle = SpanStyle(
    color = homeCardHeadersColor,
    fontWeight = FontWeight.SemiBold,
    fontSize = 14.sp
)

val homeInfoCardStartContentStyle = SpanStyle(
    color = Color.LightGray,
    fontWeight = FontWeight.Light,
    fontSize = 14.sp
)

val homeInfoCardEndContentStyle = SpanStyle(
    color = Color.LightGray,
    fontWeight = FontWeight.Thin,
    fontSize = 14.sp
)

val homeInfoCardSecondaryEndContentStyle = SpanStyle(
    color = Color.LightGray,
    fontWeight = FontWeight.Thin,
    fontSize = 12.sp
)

val aboutTextStyle = TextStyle(
    color = Color.LightGray,
    fontWeight = FontWeight.Normal
)