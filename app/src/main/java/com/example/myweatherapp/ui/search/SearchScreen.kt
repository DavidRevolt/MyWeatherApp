package com.example.myweatherapp.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myweatherapp.R
import com.example.myweatherapp.common.ScreenEvent
import com.example.myweatherapp.ui.designsystem.GetRowColors
import com.example.myweatherapp.ui.designsystem.LoadingIndicator
import com.example.myweatherapp.ui.designsystem.LoadingWheel
import com.example.myweatherapp.ui.search.components.WeatherPreviewCard
import com.example.myweatherapp.ui.search.components.WeatherSearchBar
import retrofit2.HttpException


@Composable
fun SearchScreen(
    onWeatherClick: (Int) -> Unit,
    onBackClick: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val onSearchClick = viewModel::search
    val onClearRecentSearches = viewModel::clearRecentSearches
    val swipeToStartFunc = viewModel::deleteWeather
    var showLoadingIndicator by remember { mutableStateOf(false) }
    val uiState by viewModel.searchUiState.collectAsStateWithLifecycle()
    val recentSearchQueriesUiState by viewModel.recentSearchQueriesUiState.collectAsStateWithLifecycle()

    Surface(color = Color.White) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            WeatherSearchBar(
                modifier = Modifier.fillMaxWidth(),
                onBackClick = onBackClick,
                onSearchClick = onSearchClick,
                onClearRecentSearches= onClearRecentSearches,
                recentSearchQueriesUiState = recentSearchQueriesUiState,
                placeHolder = stringResource(R.string.searchplaceHolder),
            )
            when (uiState) {
                is SearchUiState.Success -> {
                    WeatherPreviewCard(
                        modifier = Modifier.fillMaxWidth(),
                        weatherList = (uiState as SearchUiState.Success).data,
                        onRowClick = { weatherId ->
                            onWeatherClick(weatherId)
                        },
                        swipeToStartFunc = swipeToStartFunc,
                        colors = GetRowColors()
                    )

                    if (showLoadingIndicator) {
                        LoadingIndicator(stringResource(R.string.loading))
                    }
                }

                is SearchUiState.Empty -> Text(stringResource(R.string.nothing_to_show_here_yet))
                is SearchUiState.Loading -> LoadingWheel()
            }
        }
    }


    // Screen Events UI Update
    LaunchedEffect(Unit) {
        viewModel.screenEvent.collect {
            when (it) {
                is ScreenEvent.Success -> {
                    showLoadingIndicator = false
                    onShowSnackbar(it.data, null)
                }

                is ScreenEvent.Loading -> showLoadingIndicator = true

                is ScreenEvent.Error -> {
                    showLoadingIndicator = false
                    val message =
                        if (it.exception is HttpException) context.resources.getString(R.string.server_unable_to_find_location) else context.resources.getString(
                                                    R.string.unable_to_connect)
                    onShowSnackbar(message, null)
                }
            }
        }
    }
}



