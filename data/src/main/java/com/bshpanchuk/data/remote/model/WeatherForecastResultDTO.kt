package com.bshpanchuk.data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherForecastResultDTO(
    @SerialName("cnt")
    val cnt: Int,
    @SerialName("cod")
    val cod: String,
    @SerialName("list")
    val list: List<ForecastWeatherDTO>,
    @SerialName("message")
    val message: Int
)