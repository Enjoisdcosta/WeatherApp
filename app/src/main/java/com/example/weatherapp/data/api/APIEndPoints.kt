package com.example.weatherapp.data.api

import com.example.weatherapp.data.model.Weather
import retrofit2.http.GET
import retrofit2.http.Query

interface APIEndPoints {

    @GET(APIDetails.WEATHER_ENDPOINT)
    suspend fun getWeatherByCity(
        @Query("q") query: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): Weather


}