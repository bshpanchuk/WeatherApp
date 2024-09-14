package com.bshpanchuk.data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastWeatherDTO(
    @SerialName("clouds")
    val clouds: CloudsDTO,
    @SerialName("dt")
    val dt: Long,
    @SerialName("dt_txt")
    val dtTxt: String,
    @SerialName("main")
    val main: MainDTO,
    @SerialName("pop")
    val pop: Double,
    @SerialName("rain")
    val rain: PrecipitationDTO?,
    @SerialName("snow")
    val snow: PrecipitationDTO?,
    @SerialName("visibility")
    val visibility: Int,
    @SerialName("weather")
    val weather: List<WeatherDTO>,
    @SerialName("wind")
    val wind: WindDTO
)