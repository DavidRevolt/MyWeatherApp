package com.example.myweatherapp.data.recentSearchRepository

import com.example.myweatherapp.data.recentSearchRepository.local.RecentSearchQueryDao
import com.example.myweatherapp.data.recentSearchRepository.local.model.RecentSearchQueryEntity
import com.example.myweatherapp.data.recentSearchRepository.modelMappers.asExternalModel
import com.example.myweatherapp.model.RecentSearchQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import javax.inject.Inject

class RecentSearchRepositoryImpl @Inject constructor(
    private val recentSearchQueryDao: RecentSearchQueryDao,
) : RecentSearchRepository {
    override suspend fun insertOrReplaceRecentSearch(searchQuery: String) {
        recentSearchQueryDao.insertOrReplaceRecentSearchQuery(
            RecentSearchQueryEntity(
                query = searchQuery,
                timeStamp = Instant.now()
            ),
        )
    }

    override fun getRecentSearchQueries(limit: Int): Flow<List<RecentSearchQuery>> =
        recentSearchQueryDao.getRecentSearchQueries(limit).map { searchQueries ->
            searchQueries.map {
                it.asExternalModel()
            }
        }

    override suspend fun clearRecentSearches() = recentSearchQueryDao.clearRecentSearchQueries()
}