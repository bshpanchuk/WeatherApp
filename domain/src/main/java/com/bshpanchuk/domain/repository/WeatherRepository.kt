package com.bshpanchuk.domain.repository

import com.bshpanchuk.domain.model.Units
import com.bshpanchuk.domain.model.weather.AirPollution
import com.bshpanchuk.domain.model.weather.CurrentWeather
import com.bshpanchuk.domain.model.weather.Weather

interface WeatherRepository {

    suspend fun getCurrentWeather(lat: Double, lon: Double, units: Units): CurrentWeather

    suspend fun getWeatherForecast(lat: Double, lon: Double, units: Units): List<Weather>

    suspend fun getAirPollution(lat: Double, lon: Double, units: Units): AirPollution

}