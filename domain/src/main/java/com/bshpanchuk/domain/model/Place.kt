package com.bshpanchuk.domain.model

data class Place(
    val id: Long = 0,

    val lat: Double,
    val lon: Double,
    val label: String,
)