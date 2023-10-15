package com.example.myweatherapp.data.recentsearchrepository.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.myweatherapp.data.recentsearchrepository.local.model.RecentSearchQueryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentSearchQueryDao {
    @Query("SELECT * FROM recentSearchQueries ORDER BY timeStamp DESC LIMIT :limit")
    fun getRecentSearchQueries(limit: Int): Flow<List<RecentSearchQueryEntity>>

    @Upsert
    suspend fun insertOrReplaceRecentSearchQuery(recentSearchQuery: RecentSearchQueryEntity)

    @Query("DELETE FROM recentSearchQueries")
    suspend fun clearRecentSearchQueries()
}