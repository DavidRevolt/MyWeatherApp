package com.example.myweatherapp.data.recentsearchrepository.modelmappers

import com.example.myweatherapp.data.recentsearchrepository.local.model.RecentSearchQueryEntity
import com.example.myweatherapp.model.RecentSearchQuery

fun RecentSearchQueryEntity.asExternalModel() = RecentSearchQuery(
    query = query,
    timeStamp = timeStamp,
)