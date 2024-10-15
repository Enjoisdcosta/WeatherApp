package com.example.weatherapp.data.api

import com.example.weatherapp.data.model.Weather
import retrofit2.http.GET
import retrofit2.http.Query

interface APIEndPoints {

    @GET(APIDetails.WEATHER_ENDPOINT)
    suspend fun getWeatherByCity(
        @Query("q") query: String,        // City name, state code, and country code query
        @Query("appid") apiKey: String,   // API key
        @Query("units") units: String = "metric"  // Unit of measurement (metric for Celsius)
    ): Weather


}