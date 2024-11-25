package com.damien.damienliscio_comp304lab3_ex1.data

class WeatherRepository(private val weatherApi: WeatherAPI) {
    suspend fun getWeather(city: String, apiKey: String): Weather? {
        val response = weatherApi.getWeather(city, apiKey)

        if (response.isSuccessful) {
            return response.body()
        } else {
            return null
        }
    }
}