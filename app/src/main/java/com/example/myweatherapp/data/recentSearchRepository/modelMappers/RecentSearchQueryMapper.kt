package com.example.myweatherapp.data.recentSearchRepository.modelMappers

import com.example.myweatherapp.data.recentSearchRepository.local.model.RecentSearchQueryEntity
import com.example.myweatherapp.model.RecentSearchQuery

fun RecentSearchQueryEntity.asExternalModel() = RecentSearchQuery(
    query = query,
    timeStamp = timeStamp,
)