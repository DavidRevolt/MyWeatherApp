package com.example.myweatherapp.data.weatherrepository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.myweatherapp.data.weatherrepository.local.model.WeatherEntity
import com.example.myweatherapp.data.weatherrepository.local.model.WeatherForecastEntity
import com.example.myweatherapp.data.weatherrepository.local.model.WeatherWithWeatherForecast
import kotlinx.coroutines.flow.Flow

/**
 * Entity[Model] Weather have many Forecast -> One to many relationship
 * With RoomDb we create a Data Class for that: WeatherWithWeatherForecast that holds the parent Entity[The Weather] and his children [Forecasts]
 * In the child entity [Forecast] we define foreignKeys, thats creating the relationship between parent[WeatherEntity] and the Child [ ForecastEntity]
 * Because we defined foreignKeys in the child entity, when parent deleted -> child automatically deleted
 * Notice: When fetching the parent from DB, we return the third entity [WeatherWithWeatherForecast] -> the parent and list of his children
 */

@Dao
interface WeatherDao {
    @Transaction //For one to many relation, use in GET query[WeatherWithWeatherForecast contains 2 queries]
    @Query("SELECT * FROM weather WHERE weatherId = :id")
    fun getWeather(id: Int): Flow<List<WeatherWithWeatherForecast>>

    @Transaction //For one to many relation, use in GET query
    @Query("SELECT * FROM weather ORDER BY timestamp")
    fun getAllWeather(): Flow<List<WeatherWithWeatherForecast>>

    @Transaction //For one to many relation, use in GET query
    @Query("SELECT * FROM weather WHERE datetime(timestamp,'unixepoch','localtime')<datetime('now','localtime','start of day','+9 hours')")
    suspend fun getOutdatedWeatherList(): List<WeatherWithWeatherForecast>


    //WeatherForecast will be deleted automatically because WeatherForecastEntity defined foreignKeys
    @Query("DELETE FROM weather WHERE weatherId=:id")
    suspend fun deleteWeather(id: Int)

    //WeatherForecast will be deleted automatically because WeatherForecastEntity defined foreignKeys
    @Query("DELETE FROM weather")
    suspend fun deleteAllWeather()

    /**
     * on WeatherForecastEntity we defined onDelete = ForeignKey.CASCADE -> if weather parent DELETED -> forecast auto deleted!
     *
     * If we try to UPSERT like: @Upsert weatherEntity: WeatherEntity, weatherForecastEntities: List<WeatherForecastEntity>:
     * Room identify WeatherEntity by its ID and will be UPDATE the entry [but not DELETE the old one and insert the new one]
     * furthermore, Room CAN'T UPDATE weatherForecastEntities because the network side don't assign them unique ID,
     * so its just insert more weatherForecastEntities [which in that case we don't want more entries, we want the update existing]
     * In conclusion: weatherEntity will be updated and more weatherForecastEntities will be inserted
     *
     * Solution: @Insert(onConflict = OnConflictStrategy.REPLACE) and not @Upsert,
     * that will make sure if weatherEntity already exists it will be DELETED,
     * that will cause all of its weatherForecastEntities children to be DELETED [we defined onDelete = ForeignKey.CASCADE]
     * after all of that the new weatherEntity will be inserted with new weatherForecastEntities
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    suspend fun upsertWeatherWithForecast(
        weatherEntity: WeatherEntity,
        weatherForecastEntities: List<WeatherForecastEntity>
    )

}