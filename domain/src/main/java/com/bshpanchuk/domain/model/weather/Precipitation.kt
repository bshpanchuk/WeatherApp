package com.bshpanchuk.domain.model.weather

import kotlinx.serialization.Serializable

@Serializable
data class Precipitation(
    val lastHour: Double,
    val lastThreeHours: Double
)