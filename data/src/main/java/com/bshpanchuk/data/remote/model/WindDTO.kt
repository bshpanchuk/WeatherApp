package com.bshpanchuk.data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WindDTO(
    @SerialName("deg")
    val deg: Int = 0,
    @SerialName("gust")
    val gust: Double = 0.0,
    @SerialName("speed")
    val speed: Double = 0.0
)