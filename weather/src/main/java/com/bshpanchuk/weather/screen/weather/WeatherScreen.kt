package com.bshpanchuk.weather.screen.weather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bshpanchuk.domain.model.weather.Weather
import com.bshpanchuk.domain.model.weather.Wind
import com.bshpanchuk.presentation.theme.WeatherAppTheme
import com.bshpanchuk.weather.R
import com.bshpanchuk.weather.screen.weather.elements.CurrentWeather
import com.bshpanchuk.weather.screen.weather.elements.DefaultLeftCellContent
import com.bshpanchuk.weather.screen.weather.elements.HourlyWeather
import com.bshpanchuk.weather.screen.weather.elements.WeatherCell
import java.time.LocalDateTime

@Composable
fun WeatherScreen(weatherState: WeatherState) {

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            horizontal = 16.dp,
        ),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item(span = {
            GridItemSpan(2)
        }) {
            CurrentWeather(weather = weatherState.currentWeather!!)
        }

        if (weatherState.hourlyWeather?.isNotEmpty() == true) {
            item(span = {
                GridItemSpan(2)
            }) {
                HourlyWeather(weatherState.hourlyWeather)
            }
        }

        item(span = {
            GridItemSpan(2)
        }) {
            Text(text = stringResource(R.string.current_conditionals))
        }

        item(span = {
            GridItemSpan(1)
        }) {
            val wind = weatherState.currentWeather?.wind?.speed ?: 0.0
            WeatherCell(
                title = stringResource(R.string.wind),
                leftContent = { m ->
                    DefaultLeftCellContent(
                        modifier = m,
                        title = wind.toString(),
                        desc = stringResource(R.string.m_s)
                    )
                },
                rightContent = {}
            )
        }

        item(span = {
            GridItemSpan(1)
        }) {
            val humidity = weatherState.currentWeather?.humidity ?: 0
            WeatherCell(
                title = stringResource(R.string.humidity),
                leftContent = { m ->
                    DefaultLeftCellContent(
                        modifier = m,
                        title = humidity.toString(),
                        desc = "%"
                    )
                },
                rightContent = {}
            )
        }


        item(span = {
            GridItemSpan(1)
        }) {
            val pressure = weatherState.currentWeather?.pressure ?: 0
            WeatherCell(
                title = stringResource(R.string.pressure),
                leftContent = { m ->
                    DefaultLeftCellContent(
                        modifier = m,
                        title = pressure.toString(),
                        desc = stringResource(R.string.mbar)
                    )
                },
                rightContent = {}
            )
        }

        item(span = {
            GridItemSpan(1)
        }) {
            val pollution = weatherState.airPollution ?: return@item
            val desc = stringResource(
                when (pollution) {
                    1 -> R.string.aqi_good
                    2 -> R.string.aqi_fair
                    3 -> R.string.aqi_moderate
                    4 -> R.string.aqi_poor
                    else -> R.string.aqi_very_poor
                }
            )
            WeatherCell(
                title = stringResource(R.string.air_quality),
                leftContent = { m ->
                    DefaultLeftCellContent(
                        modifier = m,
                        title = pollution.toString(),
                        desc = desc
                    )
                },
                rightContent = {}
            )
        }
    }
}

@Preview
@Composable
private fun WeatherScreenPreview() {
    WeatherAppTheme {
        Scaffold { _ ->
            WeatherScreen(
                WeatherState(
                    currentWeather = Weather(
                        clouds = 1167,
                        feelsLike = 84.85,
                        humidity = 2881,
                        pressure = 1999,
                        temp = 86.87,
                        tempMax = 88.89,
                        tempMin = 90.91,
                        visibility = 3923,
                        weather = "suscipiantur",
                        wind = Wind(deg = 8425, gust = 92.93, speed = 94.95),
                        rain = null,
                        snow = null,
                        timestamp = LocalDateTime.now(),
                        weatherDescription = "elementum",
                        weatherIcon = "04d",

                        ),
                    airPollution = 3
                )
            )

        }

    }
}