package com.bshpanchuk.weather.screen

import com.bshpanchuk.domain.model.weather.WeatherResult
import com.bshpanchuk.weather.screen.weather.WeatherState

fun WeatherResult.toUiState(): WeatherState {
    val hourly = forecast.take(8)
    return WeatherState(
        currentWeather = current.copy(tempMax = hourly.maxOf { it.temp }, tempMin = hourly.minOf { it.temp }),
        hourlyWeather = hourly,
        city = city,
        airPollution = airPollution
    )
}