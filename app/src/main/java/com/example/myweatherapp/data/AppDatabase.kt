package com.example.myweatherapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myweatherapp.data.recentSearchRepository.local.RecentSearchQueryDao
import com.example.myweatherapp.data.recentSearchRepository.local.model.RecentSearchQueryEntity
import com.example.myweatherapp.data.weatherRepository.local.WeatherDao
import com.example.myweatherapp.data.weatherRepository.local.model.WeatherEntity
import com.example.myweatherapp.data.weatherRepository.local.model.WeatherForecastEntity
import com.example.myweatherapp.utils.DatabaseConverters

@Database(
    entities = [RecentSearchQueryEntity::class, WeatherEntity::class, WeatherForecastEntity::class],
    version = 1
)
//Converters In Use For RoomDB complex objects
@TypeConverters(
    DatabaseConverters::class
)
abstract class AppDatabase : RoomDatabase() {
    //List Of Dao's
    abstract fun weatherDao(): WeatherDao
    abstract fun recentSearchQueryDao(): RecentSearchQueryDao
}