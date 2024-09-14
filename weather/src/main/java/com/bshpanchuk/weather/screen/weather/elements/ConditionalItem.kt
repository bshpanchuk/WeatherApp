package com.bshpanchuk.weather.screen.weather.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bshpanchuk.presentation.theme.WeatherAppTheme
import com.bshpanchuk.presentation.utils.formatTemperature
import com.bshpanchuk.presentation.utils.getOpenWeatherIconUrl

@Composable
fun ConditionItem(
    temp: Double,
    weatherDescription: String,
    weatherIcon: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(weatherIcon.getOpenWeatherIconUrl())
                .crossfade(true)
                .build(),
            contentDescription = weatherDescription,
            modifier = Modifier
                .size(72.dp),
            contentScale = ContentScale.Crop,
        )
        Text(
            text = weatherDescription,
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = formatTemperature(temp),
            style = MaterialTheme.typography.displayLarge
                .plus(
                    TextStyle(
                        Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.onSurface,
                                MaterialTheme.colorScheme.surface
                            )
                        )
                    )
                ),
            fontSize = 96.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()

        )
    }
}

@Preview
@Composable
private fun ConditionItemPreview() {
    WeatherAppTheme {
        ConditionItem(
            temp = 12.2,
            weatherDescription = "",
            weatherIcon = "04d"
        )
    }
}