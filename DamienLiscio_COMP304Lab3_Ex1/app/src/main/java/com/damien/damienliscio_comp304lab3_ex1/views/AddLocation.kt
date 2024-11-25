package com.damien.damienliscio_comp304lab3_ex1.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.damien.damienliscio_comp304lab3_ex1.data.SavedLocation
import com.damien.damienliscio_comp304lab3_ex1.data.Weather
import com.damien.damienliscio_comp304lab3_ex1.viewModel.SavedLocationsViewModel
import com.damien.damienliscio_comp304lab3_ex1.viewModel.WeatherViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddLocation(modifier: Modifier) {
    val savedLocationsViewModel: SavedLocationsViewModel = koinViewModel()
    val weatherViewModel: WeatherViewModel = koinViewModel()
    var savedLocation by remember { mutableStateOf(TextFieldValue("")) }
    var weatherData by remember { mutableStateOf<Weather?>(null) }
    var showWeather by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .padding(20.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TextField(
            value = savedLocation,
            onValueChange = { savedLocation = it },
            label = { Text("Search New Location") },
            modifier = modifier
                .fillMaxWidth()
        )
        Button(
            onClick = {
                val location = SavedLocation(
                    savedLocation = savedLocation.text
                )
                savedLocationsViewModel.addSavedLocation(location)
                savedLocation = TextFieldValue("")
                weatherViewModel.getWeather(savedLocation.text, "4d0cd07a784ba31ba5dd16fa54af8804")
                weatherViewModel.weatherData.observeForever { response ->
                    weatherData = response
                    showWeather = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text("Add New Location")
        }
        if (showWeather && weatherData != null) {
            WeatherDisplay(weatherData = weatherData!!)
        }
    }
}

