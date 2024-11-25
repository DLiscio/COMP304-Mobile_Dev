package com.damien.damienliscio_comp304lab3_ex1.data

data class Weather (
    val main: Main,
    val weather: List<WeatherData>,
    val name: String  // Location name
)

data class Main(
    val temp: Double,  // Temperature
    val humidity: Int  // Humidity
)

data class WeatherData(
    val description: String,  // Weather condition (e.g., sunny, rainy)
    val icon: String  // Weather icon code
)