package com.bshpanchuk.data.remote.api

import com.bshpanchuk.data.BuildConfig
import com.bshpanchuk.data.remote.model.CurrentWeatherDTO
import com.bshpanchuk.data.remote.model.WeatherForecastResultDTO
import com.bshpanchuk.data.remote.model.airpollution.AirPollutionResultDTO
import com.bshpanchuk.domain.model.Units
import com.bshpanchuk.domain.model.Units.*
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.http.path
import javax.inject.Inject

class WeatherService @Inject constructor(
    private val client: HttpClient
) {

    suspend fun getCurrentWeather(lat: Double, lon: Double, units: Units): CurrentWeatherDTO {
         return client.get {
             url {
                 protocol = URLProtocol.HTTPS
                 host = OPEN_WEATHER_BASE_API
                 path("weather")
                 appendParams(lat, lon, units)
             }
         }.body<CurrentWeatherDTO>()
    }

    suspend fun getAirPollution(lat: Double, lon: Double, units: Units): AirPollutionResultDTO {
        return client.get {
            url {
                protocol = URLProtocol.HTTPS
                host = OPEN_WEATHER_BASE_API
                path("air_pollution")
                appendParams(lat, lon, units)
            }
        }.body<AirPollutionResultDTO>()
    }

    suspend fun getWeatherForecast(lat: Double, lon: Double, units: Units): WeatherForecastResultDTO {
        return client.get {
            url {
                protocol = URLProtocol.HTTPS
                host = OPEN_WEATHER_BASE_API
                path("forecast")
                appendParams(lat, lon, units)
            }
        }.body<WeatherForecastResultDTO>()
    }

    private fun URLBuilder.appendParams(lat: Double, lon: Double, units: Units) {
        parameters.append("lat", lat.toString())
        parameters.append("lon", lon.toString())
        parameters.append("appid", BuildConfig.OPEN_WEATHER_API_KEY)
        val unitParam = when (units) {
            STANDART -> "standart"
            METRIC -> "metric"
            IMPERIAL -> "imperial"
        }
        parameters.append("units", unitParam)
    }

    companion object {
        private const val OPEN_WEATHER_BASE_API = "api.openweathermap.org/data/2.5"
    }

}