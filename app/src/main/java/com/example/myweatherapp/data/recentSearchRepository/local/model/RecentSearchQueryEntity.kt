package com.example.myweatherapp.data.recentSearchRepository.local.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myweatherapp.model.RecentSearchQuery
import java.time.Instant

@Entity( tableName = "recentSearchQueries")
data class RecentSearchQueryEntity(
    @PrimaryKey
    val query: String,
    @ColumnInfo
    val timeStamp: Instant
)

fun RecentSearchQueryEntity.asExternalModel() = RecentSearchQuery(
    query = query,
    timeStamp = timeStamp,
)