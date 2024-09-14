package com.bshpanchuk.data.mapper

import com.bshpanchuk.data.remote.model.CoordDTO
import com.bshpanchuk.data.remote.model.CurrentWeatherDTO
import com.bshpanchuk.data.remote.model.PrecipitationDTO
import com.bshpanchuk.data.remote.model.WeatherForecastResultDTO
import com.bshpanchuk.data.remote.model.WindDTO
import com.bshpanchuk.data.remote.model.airpollution.AirPollutionResultDTO
import com.bshpanchuk.domain.model.weather.AirPollution
import com.bshpanchuk.domain.model.weather.City
import com.bshpanchuk.domain.model.weather.Location
import com.bshpanchuk.domain.model.weather.CurrentWeather
import com.bshpanchuk.domain.model.weather.Precipitation
import com.bshpanchuk.domain.model.weather.Weather
import com.bshpanchuk.domain.model.weather.Wind
import java.time.Instant
import java.time.LocalDateTime
import java.util.TimeZone

fun CurrentWeatherDTO.toDomain(): CurrentWeather {

    val rawWeather = weather.first()
    val weather = Weather(
        timestamp = dt.toLocalDateTime(),

        feelsLike = main.feelsLike,
        humidity = main.humidity,
        pressure = main.pressure,
        temp = main.temp,
        tempMax = main.tempMax,
        tempMin = main.tempMin,

        weather = rawWeather.main,
        weatherDescription = rawWeather.description,
        weatherIcon = rawWeather.icon,

        visibility = visibility,
        wind = wind.toDomain(),
        clouds = clouds.all,
        rain = rain?.toDomain(),
        snow = snow?.toDomain()
    )

    val city = City(
        city = name,
        cityId = id,
        cityTimezone = timezone,
        sunrise = sys.sunrise,
        sunset = sys.sunrise,
        coord = coord.toDomain()
    )

    return CurrentWeather(
        weather = weather,
        city = city
    )
}

fun CoordDTO.toDomain(): Location {
    return Location(lat = lat, lon = lon)
}

fun WindDTO.toDomain(): Wind {
    return Wind(
        speed = speed,
        deg = deg,
        gust = gust
    )
}

fun PrecipitationDTO.toDomain(): Precipitation {
    return Precipitation(
        lastHour = lastHour ?: 0.0,
        lastThreeHours = lastThreeHours ?: 0.0,
    )
}

fun AirPollutionResultDTO.toDomain(): AirPollution {
    val pollution = list.first()
    return AirPollution(
        aqi = pollution.main.aqi,
        co = pollution.components.co,
        nh3 = pollution.components.nh3,
        no = pollution.components.no,
        no2 = pollution.components.no2,
        o3 = pollution.components.o3,
        pm10 = pollution.components.pm10,
        pm25 = pollution.components.pm25,
        so2 = pollution.components.so2
    )
}

fun WeatherForecastResultDTO.toDomain(): List<Weather> {
    return list.map { rawList ->
        Weather(
            timestamp = rawList.dt.toLocalDateTime(),

            feelsLike = rawList.main.feelsLike,
            humidity = rawList.main.humidity,
            pressure = rawList.main.pressure,
            temp = rawList.main.temp,
            tempMax = rawList.main.tempMax,
            tempMin = rawList.main.tempMin,

            weather = rawList.weather.first().main,
            weatherDescription = rawList.weather.first().description,
            weatherIcon = rawList.weather.first().icon,

            visibility = rawList.visibility,
            wind = rawList.wind.toDomain(),
            clouds = rawList.clouds.all,
            rain = rawList.rain?.toDomain(),
            snow = rawList.snow?.toDomain()
        )
    }
}

fun Long.toLocalDateTime(): LocalDateTime {
    return LocalDateTime.ofInstant(
        Instant.ofEpochSecond(this),
        TimeZone.getDefault().toZoneId()
    )
}
