package com.example.myweatherapp.model

import java.time.Instant

data class RecentSearchQuery(
    val query: String,
    val timeStamp: Instant
)

