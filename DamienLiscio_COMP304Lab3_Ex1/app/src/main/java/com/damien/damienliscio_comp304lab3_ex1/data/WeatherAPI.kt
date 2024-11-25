package com.damien.damienliscio_comp304lab3_ex1.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
        @GET("weather")
        suspend fun getWeather(
            @Query("q") location: String,  // city name
            @Query("appid") apiKey: String = "4d0cd07a784ba31ba5dd16fa54af8804",  // OpenWeather API key
            @Query("units") units: String = "metric"  // Celsius temperature units
        ): Response<Weather>
}