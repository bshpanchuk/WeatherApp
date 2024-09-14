package com.bshpanchuk.domain.model.weather

import com.bshpanchuk.domain.model.serializer.DateSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class Weather(
    @Serializable(DateSerializer::class)
    val timestamp: LocalDateTime,

    val weather: String,
    val weatherDescription: String,
    val weatherIcon: String,

    val clouds: Int,
    val wind: Wind,
    val visibility: Int,

    val feelsLike: Double,
    val humidity: Int,
    val pressure: Int,
    val temp: Double,
    val tempMax: Double,
    val tempMin: Double,

    val rain: Precipitation?,
    val snow: Precipitation?
)