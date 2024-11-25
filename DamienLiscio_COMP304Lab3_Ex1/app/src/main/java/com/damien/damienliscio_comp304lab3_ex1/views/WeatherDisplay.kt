package com.damien.damienliscio_comp304lab3_ex1.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.damien.damienliscio_comp304lab3_ex1.data.Weather

@Composable
fun WeatherDisplay(weatherData: Weather) {
    Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("City: ${weatherData.name}", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text("Temperature: ${weatherData.main.temp}°C", fontSize = 18.sp)
        Text("Humidity: ${weatherData.main.humidity}%", fontSize = 18.sp)
        Text("Weather: ${weatherData.weather[0].description}", fontSize = 18.sp)
    }
}