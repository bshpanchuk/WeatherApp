package com.bshpanchuk.domain.model.weather

import kotlinx.serialization.Serializable

@Serializable
data class City (
    val city: String,
    val cityId: Int,
    val cityTimezone: Int,
    val sunrise: Int,
    val sunset: Int,

    val coord: Location,
)