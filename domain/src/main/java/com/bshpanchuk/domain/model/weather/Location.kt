package com.bshpanchuk.domain.model.weather

import kotlinx.serialization.Serializable

@Serializable
class Location(
    val lat: Double,
    val lon: Double
)