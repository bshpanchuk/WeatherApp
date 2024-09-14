package com.bshpanchuk.data.repository

import com.bshpanchuk.data.mapper.toDomain
import com.bshpanchuk.data.remote.api.WeatherService
import com.bshpanchuk.domain.model.Units
import com.bshpanchuk.domain.model.weather.AirPollution
import com.bshpanchuk.domain.model.weather.CurrentWeather
import com.bshpanchuk.domain.model.weather.Weather
import com.bshpanchuk.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService,
): WeatherRepository {

    override suspend fun getCurrentWeather(lat: Double, lon: Double, units: Units): CurrentWeather {
        return weatherService.getCurrentWeather(lat, lon, units).toDomain()
    }

    override suspend fun getAirPollution(lat: Double, lon: Double, units: Units): AirPollution {
        return weatherService.getAirPollution(lat, lon, units).toDomain()
    }

    override suspend fun getWeatherForecast(lat: Double, lon: Double, units: Units): List<Weather> {
        return weatherService.getWeatherForecast(lat, lon, units).toDomain()
    }
}