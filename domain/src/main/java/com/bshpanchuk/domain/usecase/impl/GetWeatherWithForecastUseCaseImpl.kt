package com.bshpanchuk.domain.usecase.impl

import com.bshpanchuk.domain.model.Units
import com.bshpanchuk.domain.model.weather.WeatherResult
import com.bshpanchuk.domain.repository.WeatherRepository
import com.bshpanchuk.domain.usecase.api.GetWeatherWithForecastUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class GetWeatherWithForecastUseCaseImpl(
    private val weatherRepository: WeatherRepository
) : GetWeatherWithForecastUseCase {

    override suspend operator fun invoke(lat: Double, lon: Double): WeatherResult = coroutineScope {
        val units = Units.METRIC

        val currentWeatherDef = async {
            weatherRepository.getCurrentWeather(lat, lon, units)
        }

        val forecastWeatherDef = async {
            weatherRepository.getWeatherForecast(lat, lon, units)
        }

        val airPollutionDef = async {
            weatherRepository.getAirPollution(lat, lon, units)
        }

        val currentWeather = currentWeatherDef.await()

        return@coroutineScope WeatherResult(
            current = currentWeather.weather,
            city = currentWeather.city,
            forecast = forecastWeatherDef.await(),
            airPollution = airPollutionDef.await().aqi
        )
    }

}