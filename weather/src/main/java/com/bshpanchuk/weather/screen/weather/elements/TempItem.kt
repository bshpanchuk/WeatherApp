package com.bshpanchuk.weather.screen.weather.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bshpanchuk.presentation.utils.formatTemperature

@Composable
fun TempItem(
    title: String,
    temp: Double,
    modifier: Modifier = Modifier
) {
    Column(
      modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(title, style = MaterialTheme.typography.labelLarge)
        Text(formatTemperature(temp), style = MaterialTheme.typography.headlineMedium)
    }
}

@Preview
@Composable
private fun TempItemPreview() {
    TempItem(
        "Max",
        23.3
    )
}