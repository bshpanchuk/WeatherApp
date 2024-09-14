package com.bshpanchuk.presentation.utils

import java.math.RoundingMode
import java.text.NumberFormat


private val tempFormatter = NumberFormat.getNumberInstance().apply {
    maximumFractionDigits = 0
    roundingMode = RoundingMode.FLOOR
}

fun formatTemperature(temp: Double): String {
    return tempFormatter.format(temp) + "°"
}

fun String.getOpenWeatherIconUrl() = "https://openweathermap.org/img/wn/${this}.png"