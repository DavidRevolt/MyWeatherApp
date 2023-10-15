package com.example.myweatherapp.data.userdatarepository

import com.example.myweatherapp.datastore.UserPreferencesDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class UserDataRepositoryImpl @Inject constructor(private val userPreferences: UserPreferencesDataSource) :
    UserDataRepository {


    override suspend fun setWeatherIndexToFocusOn(index: Int) {
        userPreferences.setWeatherIndexToFocusOn(index)
    }

    override fun getWeatherIndexToFocusOn(): Flow<Int> =
        userPreferences.getWeatherIndexToFocusOn()
}


