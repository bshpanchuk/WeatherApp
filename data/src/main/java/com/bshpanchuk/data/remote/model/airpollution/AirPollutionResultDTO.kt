package com.bshpanchuk.data.remote.model.airpollution


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AirPollutionResultDTO(
    @SerialName("list")
    val list: List<AirPollutionDTO>
)