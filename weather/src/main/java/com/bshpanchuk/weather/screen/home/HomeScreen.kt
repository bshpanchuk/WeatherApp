package com.bshpanchuk.weather.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bshpanchuk.domain.model.Place
import com.bshpanchuk.domain.model.weather.Weather
import com.bshpanchuk.domain.model.weather.Wind
import com.bshpanchuk.weather.R
import com.bshpanchuk.weather.screen.weather.WeatherScreen
import com.bshpanchuk.weather.screen.weather.WeatherState
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    state: HomeState,
    uiEvent: (HomeUiEvent) -> Unit,
    requestPermission: () -> Unit
) {
    Scaffold(
        topBar = {
            SearchBar(
                inputField = {
                    SearchBarDefaults.InputField(
                        query = if (state.expanded) {
                            state.searchText
                        } else {
                            state.currentPlace
                        },
                        onQueryChange = { uiEvent(HomeUiEvent.OnSearch(it)) },
                        onSearch = {},
                        expanded = state.expanded,
                        onExpandedChange = { uiEvent(HomeUiEvent.SearchStateChanged(it)) },
                        placeholder = { Text(stringResource(R.string.search)) },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        trailingIcon = {
                            if (state.expanded) {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = stringResource(R.string.close),
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .clickable {
                                            uiEvent(HomeUiEvent.SearchStateChanged(false))
                                        }
                                )
                            } else if (state.currentPlace.isNotEmpty()) {
                                Icon(
                                    if(state.isFavorite) {
                                        Icons.Default.Favorite
                                    } else {
                                        Icons.Default.FavoriteBorder
                                    },
                                    contentDescription = stringResource(R.string.favorites),
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .clickable {
                                            if (state.isFavorite) {
                                                uiEvent(HomeUiEvent.RemoveFromFavorites(state.currentPlace))
                                            } else {
                                                uiEvent(HomeUiEvent.AddToFavorites)
                                            }
                                        }
                                )
                            }
                        },
                    )
                },
                expanded = state.expanded,
                onExpandedChange = { uiEvent(HomeUiEvent.SearchStateChanged(it)) },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (state.isLoading) {
                        LinearProgressIndicator(
                            Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                        )
                    } else {
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                    ) {
                        item {
                            LocationItem(requestPermission)
                        }

                        if (state.searchText.isNotEmpty()) {
                            item {
                                Text(text = stringResource(R.string.suggestion))
                                Spacer(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(1.dp)
                                        .background(MaterialTheme.colorScheme.surfaceTint)
                                )
                            }
                            items(
                                items = state.suggestions,
                                key = { it }
                            ) {
                                Text(
                                    text = it,
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .clickable {
                                            uiEvent(HomeUiEvent.OnSuggestionSelected(it))
                                        }
                                )
                            }
                        } else {
                            item {
                                Text(text = stringResource(R.string.saved))
                                Spacer(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(1.dp)
                                        .background(MaterialTheme.colorScheme.surfaceTint)
                                )
                            }
                            items(
                                items = state.favorites,
                                key = { it.id }
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            uiEvent(HomeUiEvent.OnFavoriteSelected(it))
                                        }
                                ) {
                                    Text(
                                        text = it.label,
                                        modifier = Modifier
                                            .padding(4.dp)
                                            .weight(1f)
                                            .align(Alignment.CenterVertically)
                                    )
                                    IconButton(
                                        onClick = {
                                            uiEvent(HomeUiEvent.RemoveFromFavorites(it.label))
                                        },
                                        modifier = Modifier
                                            .size(24.dp)
                                            .align(Alignment.CenterVertically)
                                            .clip(CircleShape)
                                            .padding(2.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            tint = MaterialTheme.colorScheme.secondary,
                                            contentDescription = null,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    ) { paddings ->
        Box(modifier = Modifier.padding(paddings)) {
            state.weatherState?.let {
                WeatherScreen(weatherState = it)
            }
        }
    }

}

@Composable
private fun LocationItem(requestPermission: () -> Unit) {
    OutlinedButton(
        onClick = {
            requestPermission()
        }
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Icon(Icons.Default.Place, contentDescription = null)
            Text(text = stringResource(R.string.my_location), modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Preview
@Composable
private fun MainScreenPreviewEmpty() {
    MainScreen(HomeState(
        weatherState = WeatherState(
            currentWeather = Weather(
                timestamp = LocalDateTime.now(),
                weather = "platea",
                weatherDescription = "qualisque",
                weatherIcon = "ut",
                clouds = 2100,
                wind = Wind(deg = 2089, gust = 44.45, speed = 46.47),
                visibility = 5255,
                feelsLike = 48.49,
                humidity = 8790,
                pressure = 2521,
                temp = 50.51,
                tempMax = 52.53,
                tempMin = 54.55,
                rain = null,
                snow = null
            ),
            hourlyWeather = listOf(),
            city = null,
            airPollution = null
        ),
        expanded = false,
        currentPlace = "Kyiv"
    ),
        {},
        {}
    )
}

@Preview
@Composable
private fun MainScreenPreviewSearching() {
    MainScreen(
        HomeState(
            "City",
            favorites = listOf(
                Place(
                    id = 8377,
                    lat = 4.5,
                    lon = 6.7,
                    label = "Lviv, UA"
                ),
                Place(
                    id = 8813,
                    lat = 12.13,
                    lon = 14.15,
                    label = "Kyiv, UA"
                )
            ),
            expanded = true
        ), {}, {})
}