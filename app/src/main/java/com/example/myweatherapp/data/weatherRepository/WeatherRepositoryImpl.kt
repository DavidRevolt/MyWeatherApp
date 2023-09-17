package com.example.myweatherapp.data.weatherRepository

import android.location.Location
import com.example.myweatherapp.data.utils.NetworkResponse
import com.example.myweatherapp.data.utils.Synchronizer
import com.example.myweatherapp.data.utils.changeListSync
import com.example.myweatherapp.data.weatherRepository.local.WeatherDao
import com.example.myweatherapp.data.weatherRepository.local.model.WeatherWithWeatherForecast
import com.example.myweatherapp.data.weatherRepository.modelMappers.asEntity
import com.example.myweatherapp.data.weatherRepository.modelMappers.asExternalModel
import com.example.myweatherapp.data.weatherRepository.network.WeatherNetworkDataSource
import com.example.myweatherapp.model.Weather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class WeatherRepositoryImpl @Inject constructor(
    private val weatherDao: WeatherDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
) : WeatherRepository {


    //Fetch New Weather Forecast From Network and upsert in Room
    override suspend fun getNewWeather(city: String): NetworkResponse<Weather> =
        try {
            val response = weatherNetworkDataSource.getWeatherByCity(city).asEntity()
            weatherDao.upsertWeatherWithForecast(
                response.weatherEntity,
                response.weatherForecastEntities
            )
            NetworkResponse.Success(data = response.asExternalModel())
        } catch (e: Exception) {
            NetworkResponse.Error(exception = e)
        }

    //Fetch New Weather Forecast From Network and upsert in Room
    override suspend fun getNewWeather(location: Location): NetworkResponse<Weather> =
        try {
            val response = weatherNetworkDataSource.getWeatherByCoordinates(
                location.latitude,
                location.longitude
            ).asEntity()
            weatherDao.upsertWeatherWithForecast(
                response.weatherEntity,
                response.weatherForecastEntities
            )
            NetworkResponse.Success(data = response.asExternalModel())
        } catch (e: Exception) {
            NetworkResponse.Error(exception = e)
        }


    override fun getAllWeather(): Flow<List<Weather>> =
        weatherDao.getAllWeather().map { it.map(WeatherWithWeatherForecast::asExternalModel) }


    override suspend fun deleteWeather(id: Int) =
        weatherDao.deleteWeather(id)


    override suspend fun syncWith(synchronizer: Synchronizer): Boolean {
        return synchronizer.changeListSync(
            changeListFetcher = {
                weatherDao.getOutdatedWeatherList().map { it -> it.asExternalModel() }
            },
            modelUpdater = { changedIds ->
                changedIds.forEach { id ->
                    val response = weatherNetworkDataSource.getWeatherByCityId(id).asEntity()
                    weatherDao.upsertWeatherWithForecast(
                        response.weatherEntity,
                        response.weatherForecastEntities
                    )
                }
            }
        )
    }
}