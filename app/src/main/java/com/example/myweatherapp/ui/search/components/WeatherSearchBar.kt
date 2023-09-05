package com.example.myweatherapp.ui.search.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myweatherapp.R
import com.example.myweatherapp.ui.components.LoadingWheel
import com.example.myweatherapp.ui.search.RecentSearchQueriesUiState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherSearchBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onSearchClick: (String) -> Unit = {},
    onClearRecentSearches: () -> Unit = {},
    recentSearchQueriesUiState: RecentSearchQueriesUiState,
    placeHolder: String = ""
) {
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    SearchBar(
        query = text,
        onQueryChange = { text = it },
        onSearch = {
            if (text.isNotEmpty()) {
                active = !active
                onSearchClick(it)
            }
        },
        active = active,
        onActiveChange = { active = it },
        placeholder = { Text(placeHolder) },
        leadingIcon = { Icon(Icons.Rounded.Search, contentDescription = null) },
        trailingIcon = {
            if (active) {
                Icon(
                    Icons.Rounded.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier.clickable { active = !active })
            } else
                Icon(
                    Icons.Rounded.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier.clickable(onClick = onBackClick)
                )

        },
        modifier = modifier.animateContentSize(
            animationSpec = tween(
                durationMillis = 300,
                delayMillis = 50,
                easing = FastOutLinearInEasing
            )
        )
        //colors = SearchBarDefaults.colors(containerColor = Color.White.copy(alpha = 0.4f))
    ) {
        // Active Search Content: Recent Searches
        when (recentSearchQueriesUiState) {
            is RecentSearchQueriesUiState.Success -> {
                (recentSearchQueriesUiState as RecentSearchQueriesUiState.Success).recentQueries.forEach {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 14.dp)
                        .clickable(onClick = {
                                active = !active
                                onSearchClick(it.query)
                        })) {
                        Icon(imageVector = Icons.Default.History, contentDescription = null)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = it.query)
                    }
                }
                Divider()
                Text(
                    modifier = Modifier
                        .padding(all = 14.dp)
                        .fillMaxWidth()
                        .clickable(onClick = onClearRecentSearches),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    text = stringResource(R.string.clear_all_history)
                )
            }

            is RecentSearchQueriesUiState.Loading -> LoadingWheel()

        }
    }
}