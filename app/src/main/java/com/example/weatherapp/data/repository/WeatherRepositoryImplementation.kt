package com.example.weatherapp.data.repository

import com.example.weatherapp.data.api.APIEndPoints
import com.example.weatherapp.data.model.Weather
import javax.inject.Inject

class WeatherRepositoryImplementation @Inject constructor(
    private val weatherService: APIEndPoints
) {
    suspend fun getWeatherByCity(query: String, apiKey: String): Weather {
        return weatherService.getWeatherByCity(query, apiKey)
    }
}