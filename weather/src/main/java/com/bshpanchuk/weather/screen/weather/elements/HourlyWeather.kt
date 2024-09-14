package com.bshpanchuk.weather.screen.weather.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bshpanchuk.domain.model.weather.Weather
import com.bshpanchuk.weather.R

@Composable
fun HourlyWeather(hourlyWeather: List<Weather>) {
    Column(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Text(text = stringResource(R.string.hourly))
        Spacer(modifier = Modifier.height(4.dp))
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.surfaceContainer, RoundedCornerShape(8.dp)
                )
                .padding(vertical = 4.dp)
        ) {
            items(hourlyWeather) {
                WeatherItem(it)
            }
        }
    }
}