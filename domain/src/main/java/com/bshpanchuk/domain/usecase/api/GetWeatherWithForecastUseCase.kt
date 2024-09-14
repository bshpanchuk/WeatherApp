package com.bshpanchuk.domain.usecase.api

import com.bshpanchuk.domain.model.weather.WeatherResult

interface GetWeatherWithForecastUseCase {
    suspend operator fun invoke(lat: Double, lon: Double): WeatherResult
}