package com.bshpanchuk.data.remote.model.airpollution


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AirPollutionDTO(
    @SerialName("components")
    val components: ComponentsDTO,
    @SerialName("dt")
    val dt: Int,
    @SerialName("main")
    val main: AirPollutionLevelDTO
)