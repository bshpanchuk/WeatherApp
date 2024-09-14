package com.bshpanchuk.weather.screen.weather.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
inline fun WeatherCell(
    title: String,
    leftContent: @Composable RowScope.(Modifier) -> Unit,
    rightContent: @Composable RowScope.(Modifier) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(
                MaterialTheme.colorScheme.surfaceContainer,
                RoundedCornerShape(8.dp)
            )
            .padding(12.dp)
    ) {
        Text(text = title)
        Row(modifier = Modifier.fillMaxWidth()) {
            leftContent(Modifier.weight(6f))
            rightContent(Modifier.weight(4f))
        }
    }
}

@Composable
fun DefaultLeftCellContent(
    title: String,
    desc: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Text(text = title, style = MaterialTheme.typography.headlineSmall)
        Text(text = desc, style = MaterialTheme.typography.bodyLarge)
    }
}