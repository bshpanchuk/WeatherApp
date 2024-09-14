package com.bshpanchuk.weather.screen.weather.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bshpanchuk.domain.model.weather.Weather
import com.bshpanchuk.domain.model.weather.Wind
import com.bshpanchuk.presentation.utils.DateUtils.formatToTime
import com.bshpanchuk.presentation.utils.formatTemperature
import com.bshpanchuk.presentation.utils.getOpenWeatherIconUrl
import java.time.LocalDateTime

@Composable
fun WeatherItem(
    weather: Weather,
    modifier: Modifier = Modifier
) {
    Column(
      modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(formatTemperature(weather.temp), style = MaterialTheme.typography.titleLarge)
        AsyncImage(
            model = weather.weatherIcon.getOpenWeatherIconUrl(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(32.dp)
        )
        Text(weather.timestamp.formatToTime())
    }
}

@Preview
@Composable
private fun WeatherItemPreview() {
    WeatherItem(weather = Weather(
        timestamp = LocalDateTime.now(),
        weather = "pri",
        weatherDescription = "malesuada",
        weatherIcon = "atqui",
        clouds = 7753,
        wind = Wind(
            deg = 1175,
            gust = 112.113,
            speed = 114.115
        ),
        visibility = 5779,
        feelsLike = 116.117,
        humidity = 8657,
        pressure = 7147,
        temp = 118.119,
        tempMax = 120.121,
        tempMin = 122.123,
        rain = null,
        snow = null
    ) )
}