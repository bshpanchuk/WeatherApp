package com.bshpanchuk.domain.model.weather

import kotlinx.serialization.Serializable

@Serializable
data class WeatherResult(
    val city: City,
    val current: Weather,
    val forecast: List<Weather>,
    val airPollution: Int,
)
