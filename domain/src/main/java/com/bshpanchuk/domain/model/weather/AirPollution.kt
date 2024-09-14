package com.bshpanchuk.domain.model.weather

data class AirPollution(
    val aqi: Int,

    val co: Double,
    val nh3: Double,
    val no: Double,
    val no2: Double,
    val o3: Double,
    val pm10: Double,
    val pm25: Double,
    val so2: Double
)