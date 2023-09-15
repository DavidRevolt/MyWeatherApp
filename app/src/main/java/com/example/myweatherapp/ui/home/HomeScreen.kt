package com.example.myweatherapp.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myweatherapp.R
import com.example.myweatherapp.common.ScreenEvent
import com.example.myweatherapp.model.Weather
import com.example.myweatherapp.ui.designsystem.LoadingWheel
import com.example.myweatherapp.ui.home.components.AppBarDropDownMenu
import com.example.myweatherapp.ui.home.components.InfoCard
import com.example.myweatherapp.ui.home.components.TempWidget
import com.example.myweatherapp.ui.home.components.WeatherTopAppBar
import com.example.myweatherapp.ui.home.components.WeeklyForecast
import com.example.myweatherapp.ui.designsystem.homePagerDotColor
import com.example.myweatherapp.ui.designsystem.homePagerDotCurrentColor
import com.example.myweatherapp.ui.designsystem.homePullToRefreshTextStyle
import com.github.fengdai.compose.pulltorefresh.PullToRefresh
import com.github.fengdai.compose.pulltorefresh.rememberPullToRefreshState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.launch
import retrofit2.HttpException
import kotlin.math.absoluteValue


@SuppressLint("MissingPermission")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    onAboutClick: () -> Unit,
    onSearchClick: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val uiState by viewModel.weatherUiState.collectAsStateWithLifecycle()
    val appBarTitle = remember { mutableStateOf("") }
    var isRefreshing by remember { mutableStateOf(false) }
    val dropDownMenuExpanded = remember { mutableStateOf(false) }
    val onPullToRefresh = viewModel::pullToRefresh //TODO Make This Function Active
    val locationPermissionsState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
    )
    val onGpsClickLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions.containsValue(true))
                viewModel::onGpsClick.invoke()
        }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box() {
            WeatherTopAppBar(
                title = appBarTitle.value,
                navigationIcon = Icons.Default.MoreVert,
                navigationIconContentDescription = stringResource(R.string.menu),
                searchIcon = Icons.Default.Add,
                searchIconContentDescription = stringResource(R.string.search_locations),
                gpsIcon = Icons.Default.MyLocation,
                gpsIconContentDescription = stringResource(R.string.find_my_location),
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent),
                onNavigationClick = { dropDownMenuExpanded.value = true },
                onSearchClick = onSearchClick,
                onGpsClick = {
                    if (locationPermissionsState.shouldShowRationale) {
                        scope.launch {

                            if (onShowSnackbar(
                                    context.resources.getString(R.string.permission_required),
                                    context.resources.getString(
                                        R.string.go_to_settings
                                    )
                                )
                            ) {
                                val intent = Intent(
                                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.fromParts("package", context.packageName, null)
                                )
                                ContextCompat.startActivity(context, intent, null)
                            }
                        }
                    } else {
                        onGpsClickLauncher.launch(
                            locationPermissionsState.permissions.map { it -> it.permission }
                                .toTypedArray()
                        )
                    }
                })
            AppBarDropDownMenu(
                onAboutClick = onAboutClick,
                expanded = dropDownMenuExpanded,
            )
        }
        when (uiState) {
            is WeatherUiState.Success -> {
                val data = (uiState as WeatherUiState.Success)
                HomeScreenContent(
                    data.data,
                    appBarTitle,
                    data.weatherIndexToFocusOn,
                    onPullToRefresh,
                    isRefreshing,
                )
            }

            is WeatherUiState.Empty -> Text(stringResource(R.string.nothing_to_show_here_yet))

            is WeatherUiState.Loading -> LoadingWheel()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.screenEvent.collect {
            when (it) {
                is ScreenEvent.Success -> {
                    isRefreshing = false
                    onShowSnackbar(it.data, null)
                }

                is ScreenEvent.Loading -> isRefreshing = true

                is ScreenEvent.Error -> {
                    isRefreshing = false
                    val message = when (it.exception) {
                        is HttpException -> context.resources.getString(R.string.server_unable_to_find_location)
                        is NullPointerException -> context.resources.getString(R.string.something_went_wrong)
                        else -> it.exception?.message
                    }
                    onShowSnackbar("⚠️ ${message}", null)
                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomeScreenContent(
    weatherList: List<Weather>,
    appBarTitle: MutableState<String>,
    weatherIndexToFocusOn: Int = 0,
    onPullToRefresh: () -> Unit,
    isRefreshing: Boolean,
) {
    if (weatherList.isEmpty()) return

    val pageCount = weatherList.size

    //currentPage could be 7 but the data for page 7 can be deleted from other screen, solution:
    var pagerState = key(weatherList.size) {
        rememberPagerState(
            initialPage = minOf(weatherIndexToFocusOn, weatherList.size - 1),
            initialPageOffsetFraction = 0f
        ) { pageCount }
    }

    LaunchedEffect(key1 = weatherIndexToFocusOn) {
        pagerState.scrollToPage(weatherIndexToFocusOn)
    }


    appBarTitle.value =
        weatherList[pagerState.currentPage].city + ", " + weatherList[pagerState.currentPage].country

    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        repeat(pageCount) { iteration ->
            val color =
                if (pagerState.currentPage == iteration) homePagerDotCurrentColor else homePagerDotColor
            Box(
                modifier = Modifier
                    .padding(1.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(5.dp)
            )
        }
    }

    PullToRefresh(
        state = rememberPullToRefreshState(isRefreshing = isRefreshing),
        onRefresh = onPullToRefresh,
        textStyle = homePullToRefreshTextStyle
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(8.dp, bottom = 50.dp)
        ) {
            item {
                TempWidget(
                    modifier = Modifier
                        .padding(40.dp)
                        .padding(top = 70.dp,bottom = 90.dp)
                        .size(250.dp), weatherList[pagerState.currentPage].weatherForecast[0]
                )
            }

            /* PAGER START*/
            item {
                HorizontalPager(
                    state = pagerState,
                    beyondBoundsPageCount = 5
                ) { page ->
                    val currentWeather = weatherList[page]
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .graphicsLayer {
                                val pageOffset = (
                                        (pagerState.currentPage - page) + pagerState
                                            .currentPageOffsetFraction
                                        ).absoluteValue
                                alpha = lerp(
                                    start = 0.5f,
                                    stop = 1f,
                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                )
                            },
                        verticalArrangement = Arrangement.spacedBy(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        WeeklyForecast(
                            modifier = Modifier.fillMaxWidth(),
                            forecastList = currentWeather.weatherForecast
                        )

                        //This row will show as grid
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Min), //because we use lazyColumn we set this row height to min of his child ,
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(15.dp)
                        ) {

                            val todayWeather = currentWeather.weatherForecast[0]
                            val content = listOf(
                                Triple(
                                    stringResource(R.string.humidity),
                                    todayWeather.humidity + "%",
                                    null
                                ),
                                Triple(
                                    stringResource(R.string.cloudiness),
                                    todayWeather.cloudiness + "%",
                                    null
                                ),
                                Triple(
                                    stringResource(R.string.pressure),
                                    todayWeather.atmosphericPressure,
                                    stringResource(R.string.m_bar)
                                ),
                                Triple(
                                    stringResource(R.string.wind_speed),
                                    todayWeather.windSpeed,
                                    stringResource(R.string.meter_per_second)
                                ),
                                Triple(
                                    stringResource(R.string.chance_of_rain),
                                    todayWeather.probabilityOfPrecipitation + "%",
                                    null
                                ),
                            )
                            InfoCard(modifier = Modifier.weight(1f), content = content)


                            Column(
                                verticalArrangement = Arrangement.spacedBy(5.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight() //we can use Max Height because Row Height set to (IntrinsicSize.Min) -> Min of the child
                                    .weight(1f)
                            ) {
                                InfoCard(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight()
                                        .weight(1f), //we can use MaxHeight in this card because the row modifier set to height(IntrinsicSize.Min)
                                    content = listOf(
                                        Triple(
                                            stringResource(R.string.sun_rise),
                                            todayWeather.sunrise,
                                            null
                                        ),
                                        Triple(
                                            stringResource(R.string.sun_set),
                                            todayWeather.sunset,
                                            null
                                        )
                                    ),
                                    useDivider = false
                                )
                                InfoCard(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight()
                                        .weight(1f), //we can use MaxHeight in this card because the row modifier set to height(IntrinsicSize.Min)
                                    content = listOf(
                                        Triple(
                                            stringResource(R.string.latitude),
                                            currentWeather.latitude,
                                            null
                                        ),
                                        Triple(
                                            stringResource(R.string.longitude),
                                            currentWeather.longitude,
                                            null
                                        )
                                    ),
                                    useDivider = false
                                )
                            }

                        }
                    }
                }
            }
        }
    }
}





