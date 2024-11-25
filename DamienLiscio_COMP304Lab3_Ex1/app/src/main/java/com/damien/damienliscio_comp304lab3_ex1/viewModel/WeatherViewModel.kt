package com.damien.damienliscio_comp304lab3_ex1.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.damien.damienliscio_comp304lab3_ex1.data.Weather
import com.damien.damienliscio_comp304lab3_ex1.data.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {
    private val _weatherData = MutableLiveData<Weather>()
    val weatherData: LiveData<Weather> get() = _weatherData

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getWeather(city: String, apiKey: String) {
        viewModelScope.launch {
            try {
                val response = weatherRepository.getWeather(city, apiKey)
                if (response != null) {
                    _weatherData.postValue(response!!)
                } else {
                    _error.postValue("Failed to fetch weather data")
                }
            } catch (e: Exception) {
                _error.postValue("Error: ${e.message}")
            }
        }
    }
}