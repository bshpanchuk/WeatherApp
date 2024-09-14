package com.bshpanchuk.weather.screen

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.viewModelScope
import com.bshpanchuk.domain.model.Place
import com.bshpanchuk.domain.model.weather.Location
import com.bshpanchuk.domain.model.weather.WeatherResult
import com.bshpanchuk.domain.repository.LocationRepository
import com.bshpanchuk.domain.repository.PlacesRepository
import com.bshpanchuk.domain.usecase.api.GetWeatherWithForecastUseCase
import com.bshpanchuk.presentation.mvi.Dialog
import com.bshpanchuk.presentation.mvi.MviViewModel
import com.bshpanchuk.weather.screen.home.HomeCallback
import com.bshpanchuk.weather.screen.home.HomeState
import com.bshpanchuk.weather.screen.home.HomeUiEvent
import com.google.android.gms.common.api.ResolvableApiException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class MainViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val getWeatherUseCase: GetWeatherWithForecastUseCase,
    private val placesRepository: PlacesRepository,
) : MviViewModel<HomeUiEvent, HomeState, Dialog, HomeCallback>(HomeState()) {

    init {
        observeQueryChange()
        observeFavorites()
        checkCache()
    }

    override fun handleUiEvent(uiEvent: HomeUiEvent) {
        when (uiEvent) {
            is HomeUiEvent.OnSearch -> {
                updateState {
                    copy(searchText = uiEvent.text)
                }
            }

            is HomeUiEvent.SearchStateChanged -> {
                updateState {
                    copy(expanded = uiEvent.newState)
                }
            }

            HomeUiEvent.RequestLocation -> requestLocation()
            is HomeUiEvent.OnPermissionGrantedState -> handlePermissionResult(uiEvent)
            is HomeUiEvent.OnSuggestionSelected -> handleSuggestionPick(uiEvent.address)
            HomeUiEvent.AddToFavorites -> addCurrentToFavorites()
            is HomeUiEvent.OnFavoriteSelected -> handleFavoritePick(uiEvent.place)
            is HomeUiEvent.RemoveFromFavorites -> removeFavorite(uiEvent.label)
        }
    }

    private fun removeFavorite(label: String) {
        launch {
            placesRepository.deletePlace(label)
        }
    }

    private fun addCurrentToFavorites() {
        val place = Place(
            lat = state.value.currentLocation?.lat ?: 0.0,
            lon = state.value.currentLocation?.lon ?: 0.0,
            label = state.value.currentPlace
        )

        launch {
            placesRepository.addPlaceToFavorites(place)
        }
    }

    private fun handleSuggestionPick(address: String) {
        updateState {
            copy(
                currentPlace = address,
                isFavorite = state.value.favorites.any { it.label == address },
                isLoading = true
            )
        }
        launch {
            val location = locationRepository.getLocationFromAddress(address) ?: return@launch
            loadWeather(location)
        }
    }

    private fun handleFavoritePick(place: Place) {
        updateState {
            state.value.copy(
                currentPlace = place.label,
                isFavorite = true,
                isLoading = true
            )
        }
        loadWeather(Location(place.lat, place.lon))
    }

    private fun handlePermissionResult(permissionResult: HomeUiEvent.OnPermissionGrantedState) {
        if (permissionResult.isGranted) {
            requestLocation()
        } else {
            sendCallback(HomeCallback.LocationPermissionDenied)
        }
    }

    private fun requestLocation() {
        launch {
            try {
                val location = locationRepository.getLocation()
                if (location != null) {
                    loadWeather(location)
                    decodeAddress(location)
                }
            } catch (e: ResolvableApiException) {
                sendCallback(HomeCallback.RequestEnableLocationCallback(e))
            }
        }
    }

    private fun decodeAddress(location: Location) {
        launch {
            val res = locationRepository.getAddressFromLocation(location).orEmpty()
            updateState {
                copy(
                    currentPlace = res,
                )
            }
        }
    }

    private fun loadWeather(location: Location) {
        launch(
            progress = { progress ->
                updateState {
                    copy(isLoading = progress)
                }
            },
            onError = {
                sendCallback(HomeCallback.SomethingWentWrong)
            }
        ) {
            val result = getWeatherUseCase(location.lat, location.lon)
            updateState {
                copy(
                    weatherState = result.toUiState(),
                    expanded = false,
                    currentLocation = location
                )
            }
            saveToCache(result)
        }
    }

    private fun observeQueryChange() {
        snapshotFlow { uiState.value }
            .map { it.searchText.trim().lowercase() }
            .distinctUntilChanged()
            .debounce(DEBOUNCE_DURATION)
            .mapLatest { query ->
                if (query.isBlank()) {
                    clearSuggestions()
                } else {
                    loadSuggestions(query)
                }
            }
            .launchIn(viewModelScope)
    }

    private suspend fun loadSuggestions(query: String) {
        updateState {
            copy(isLoading = true)
        }
        val result = locationRepository.getAddressAutocomplete(query, state.value.currentLocation)
        updateState {
            copy(
                isLoading = false,
                suggestions = result
            )
        }
    }

    private fun clearSuggestions() {
        updateState {
            copy(suggestions = emptyList())
        }
    }

    private fun observeFavorites() {
        placesRepository.observePlaces()
            .onEach { favorites ->
                updateState {
                    copy(
                        favorites = favorites,
                        isFavorite = favorites.any { it.label == state.value.currentPlace }
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun checkCache() {
        launch {
            val cache = placesRepository.getCache()

            if (cache != null) {
                updateState {
                   copy(
                        weatherState = cache.toUiState(),
                        expanded = false,
                        currentLocation = cache.city.coord
                    )
                }
                decodeAddress(cache.city.coord)
                loadWeather(cache.city.coord)
            } else {
                updateState {
                    copy(
                        expanded = true
                    )
                }
            }
        }
    }

    private fun saveToCache(weather: WeatherResult) {
        launch {
            placesRepository.saveCache(weather)
        }
    }

    companion object {
        private const val DEBOUNCE_DURATION = 1000L
    }
}





