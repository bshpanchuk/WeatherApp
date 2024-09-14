package com.bshpanchuk.data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PrecipitationDTO(
    @SerialName("1h")
    val lastHour: Double?,
    @SerialName("3h")
    val lastThreeHours: Double?
)