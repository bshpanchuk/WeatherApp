package com.bshpanchuk.weather.screen.home

import com.bshpanchuk.presentation.mvi.Callback
import com.google.android.gms.common.api.ResolvableApiException

sealed interface HomeCallback : Callback {
    data object RequestLocationPermission : HomeCallback
    data object LocationPermissionDenied : HomeCallback
    data object SomethingWentWrong : HomeCallback
    data class RequestEnableLocationCallback(val exception: ResolvableApiException) : HomeCallback
}