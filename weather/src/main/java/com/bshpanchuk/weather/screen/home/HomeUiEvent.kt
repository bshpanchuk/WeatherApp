package com.bshpanchuk.weather.screen.home

import com.bshpanchuk.domain.model.Place
import com.bshpanchuk.presentation.mvi.UiEvent

sealed interface HomeUiEvent : UiEvent {
    data class OnSearch(val text: String) : HomeUiEvent
    data class SearchStateChanged(val newState: Boolean) : HomeUiEvent
    data class OnPermissionGrantedState(val isGranted: Boolean) : HomeUiEvent

    data class OnSuggestionSelected(val address: String) : HomeUiEvent
    data class OnFavoriteSelected(val place: Place) : HomeUiEvent
    data class RemoveFromFavorites(val label: String) : HomeUiEvent

    data object RequestLocation: HomeUiEvent
    data object AddToFavorites: HomeUiEvent
}