package com.bshpanchuk.data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeatherDTO(
    @SerialName("base")
    val base: String,
    @SerialName("clouds")
    val clouds: CloudsDTO,
    @SerialName("dt")
    val dt: Long,
    @SerialName("id")
    val id: Int,
    @SerialName("main")
    val main: MainDTO,
    @SerialName("name")
    val name: String,
    @SerialName("sys")
    val sys: SysDTO,
    @SerialName("timezone")
    val timezone: Int,
    @SerialName("visibility")
    val visibility: Int,
    @SerialName("weather")
    val weather: List<WeatherDTO>,
    @SerialName("wind")
    val wind: WindDTO,
    @SerialName("rain")
    val rain: PrecipitationDTO?,
    @SerialName("snow")
    val snow: PrecipitationDTO?,
    @SerialName("coord")
    val coord: CoordDTO,
)