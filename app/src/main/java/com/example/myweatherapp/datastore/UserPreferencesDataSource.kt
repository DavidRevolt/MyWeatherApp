package com.example.myweatherapp.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesDataSource @Inject constructor(private val userPreferences: DataStore<Preferences>) {
    private val WEATHER_INDEX = intPreferencesKey("weather_index")

    suspend fun setWeatherIndexToFocusOn(index: Int) {
        userPreferences.edit { preferences -> preferences[WEATHER_INDEX] = index }
    }

    fun getWeatherIndexToFocusOn(): Flow<Int> {
        val result = userPreferences.data
            .catch {
                emit(emptyPreferences())
            }
            .map { preferences ->
                // Get our name value, defaulting to "" if not set
                preferences[WEATHER_INDEX] ?: 0
            }
        return result
    }
}