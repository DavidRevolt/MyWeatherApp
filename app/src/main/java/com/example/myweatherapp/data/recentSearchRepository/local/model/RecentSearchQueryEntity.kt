package com.example.myweatherapp.data.recentSearchRepository.local.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity( tableName = "recentSearchQueries")
data class RecentSearchQueryEntity(
    @PrimaryKey
    val query: String,
    @ColumnInfo
    val timeStamp: Instant
)
