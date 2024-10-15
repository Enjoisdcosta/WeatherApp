package com.example.weatherapp.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.data.repository.WeatherRepositoryImplementation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepositoryImplementation
) : ViewModel() {

    var weatherResponse: Weather? by mutableStateOf(null)
    var errorMessage: String? by mutableStateOf(null)

    fun fetchWeatherByCity(city: String, state: String?, country: String?) {
        val query = buildQuery(city, state, country)

        viewModelScope.launch {
            try {
                val response = repository.getWeatherByCity(query, "42def148c9bc0052a33b1e2a256bfc68")  // Use your API key here
                weatherResponse = response
            } catch (e: Exception) {
                errorMessage = "Error fetching weather data"
            }
        }
    }
    private fun buildQuery(city: String, state: String?, country: String?): String {
        val query = StringBuilder(city)
        if (!state.isNullOrBlank()) {
            query.append(",$state")
        }
        if (!country.isNullOrBlank()) {
            query.append(",$country")
        }
        return query.toString()
    }
}