package com.example.myweatherapp.ui.home

import android.Manifest
import androidx.annotation.RequiresPermission
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweatherapp.Services.location.LocationService
import com.example.myweatherapp.data.NetworkResponse
import com.example.myweatherapp.data.Synchronizer
import com.example.myweatherapp.data.userDataRepository.UserDataRepository
import com.example.myweatherapp.data.weatherRepository.WeatherRepository
import com.example.myweatherapp.model.Weather
import com.example.myweatherapp.ui.components.ScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val userDataRepository: UserDataRepository,
    private val lastLocationService: LocationService,
    savedStateHandle: SavedStateHandle // needed for getting navigation args
) : ViewModel(), Synchronizer {

    //the query from navigation args, Not in use in favor of DataStore
    //val pageToFocusOn = checkNotNull(savedStateHandle[weatherIdArg])

    val weatherUiState = combine( weatherRepository.getAllWeather(), userDataRepository.getWeatherIndexToFocusOn()){
        weatherList, weatherIndexToFocusOn ->
        if (weatherList.isEmpty()) WeatherUiState.Empty else WeatherUiState.Success(
            weatherIndexToFocusOn = weatherIndexToFocusOn,
            data = weatherList
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = WeatherUiState.Loading
    )



    val weatherUiState2 = weatherRepository.getAllWeather()
        .map { result ->
            if (result.isEmpty()) WeatherUiState.Empty else WeatherUiState.Success(
                weatherIndexToFocusOn = 0,
                data = result
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = WeatherUiState.Loading
        )


    //Send one time event to Screen
    //Does not need an initial value so does not emit any value by default
    //Does not store any data.
    private val _screenEvent = MutableSharedFlow<ScreenEvent<String>>()//Initial statues
    val screenEvent = _screenEvent.asSharedFlow() //for screen to claim as read only

    fun pullToRefresh() {
        viewModelScope.launch {
            _screenEvent.emit(ScreenEvent.Loading)
            val syncedSuccessfully = awaitAll(async { weatherRepository.sync() }).all { it }
            if (syncedSuccessfully) {
                _screenEvent.emit(ScreenEvent.Success("✔️ All up to date!"))
            } else {
                _screenEvent.emit(ScreenEvent.Error(Throwable(message = "Update failed...")))
            }
        }
    }


    @RequiresPermission(
        anyOf = [
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION]
    )
    fun onGpsClick() {
        viewModelScope.launch {
            _screenEvent.emit(ScreenEvent.Loading)
            val result = lastLocationService.getLocation()
            val location = result.getOrNull() //Can return null if no last location available
            if (location != null) {
                val response = weatherRepository.getNewWeather(location)
                when (response) {
                    is NetworkResponse.Success -> {
                        _screenEvent.emit(ScreenEvent.Success("✔️ ${response.data.city} added successfully"))
                    }

                    is NetworkResponse.Error -> {
                        _screenEvent.emit(ScreenEvent.Error(response.exception))
                    }
                }
            } else
                _screenEvent.emit(ScreenEvent.Error(exception = Exception("No Gps Signal")))
        }
    }
}


sealed interface WeatherUiState {
    data class Success(
        val weatherIndexToFocusOn: Int,
        val data: List<Weather>
    ) : WeatherUiState

    object Empty : WeatherUiState
    object Loading : WeatherUiState
}

