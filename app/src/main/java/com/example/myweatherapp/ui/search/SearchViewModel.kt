package com.example.myweatherapp.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweatherapp.data.NetworkResponse
import com.example.myweatherapp.data.recentSearchRepository.RecentSearchRepository
import com.example.myweatherapp.data.weatherRepository.WeatherRepository
import com.example.myweatherapp.model.RecentSearchQuery
import com.example.myweatherapp.model.Weather
import com.example.myweatherapp.ui.components.ScreenEvent
import com.example.myweatherapp.ui.home.WeatherUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val recentSearchRepository: RecentSearchRepository
) :
    ViewModel() {

    //UiState
    val searchUiState = weatherRepository.getAllWeather()
        .map { result ->
            if (result.isEmpty()) SearchUiState.Empty else SearchUiState.Success(data = result)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = WeatherUiState.Loading
        )


    val recentSearchQueriesUiState = recentSearchRepository.getRecentSearchQueries(SEARCH_QUERY_LIMIT)
            .map(RecentSearchQueriesUiState::Success)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = RecentSearchQueriesUiState.Loading,
            )

    fun deleteWeather(id: Int) {
        viewModelScope.launch {
            weatherRepository.deleteWeather(id)
        }
    }

    //Send one time event to Screen
    //Does not need an initial value so does not emit any value by default
    //Does not store any data.
    private val _screenEvent = MutableSharedFlow<ScreenEvent<String>>()//Initial statues
    val screenEvent = _screenEvent.asSharedFlow() //for screen to claim as read only


    fun search(query: String) {
        viewModelScope.launch {
            _screenEvent.emit(ScreenEvent.Loading)
            recentSearchRepository.insertOrReplaceRecentSearch(searchQuery = query)
            val response = weatherRepository.getNewWeather(query)
            when (response) {
                is NetworkResponse.Success -> {
                    _screenEvent.emit(ScreenEvent.Success("✔️ ${response.data.city} added successfully"))
                }

                is NetworkResponse.Error -> {
                    _screenEvent.emit(ScreenEvent.Error(response.exception))
                }
            }
        }
    }



    fun clearRecentSearches() {
        viewModelScope.launch {
            recentSearchRepository.clearRecentSearches()
        }}

}


sealed interface SearchUiState {
    data class Success(val data: List<Weather>) : SearchUiState
    object Empty : SearchUiState
    object Loading : SearchUiState
}


sealed interface RecentSearchQueriesUiState {
     object Loading : RecentSearchQueriesUiState

    data class Success(
        val recentQueries: List<RecentSearchQuery> = emptyList(),
    ) : RecentSearchQueriesUiState
}
private const val SEARCH_QUERY_LIMIT = 10