package com.example.myweatherapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myweatherapp.data.recentsearchrepository.local.RecentSearchQueryDao
import com.example.myweatherapp.data.recentsearchrepository.local.model.RecentSearchQueryEntity
import com.example.myweatherapp.data.utils.DatabaseConverters
import com.example.myweatherapp.data.weatherrepository.local.WeatherDao
import com.example.myweatherapp.data.weatherrepository.local.model.WeatherEntity
import com.example.myweatherapp.data.weatherrepository.local.model.WeatherForecastEntity

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