package com.bshpanchuk.weather.screen.weather

import com.bshpanchuk.domain.model.weather.City
import com.bshpanchuk.domain.model.weather.Weather

data class WeatherState(
    val currentWeather: Weather? = null,
    val hourlyWeather: List<Weather>? = emptyList(),
    val city: City? = null,
    val airPollution: Int? = 0,
)