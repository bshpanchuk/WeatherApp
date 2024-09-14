package com.bshpanchuk.weather.screen.home

import androidx.compose.runtime.Stable
import com.bshpanchuk.domain.model.Place
import com.bshpanchuk.domain.model.weather.Location
import com.bshpanchuk.presentation.mvi.UiState
import com.bshpanchuk.weather.screen.weather.WeatherState
import java.time.LocalDateTime

@Stable
data class HomeState(
    val currentPlace: String = "",
    val currentLocation: Location? = null,
    val isFavorite:  Boolean = false,
    val isLoading: Boolean = false,
    val searchText: String = "",
    val expanded: Boolean = false,
    val suggestions: List<String> = emptyList(),
    val favorites: List<Place> = emptyList(),

    val weatherState: WeatherState? = null
) : UiState