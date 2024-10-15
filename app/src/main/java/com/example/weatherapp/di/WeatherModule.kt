package com.example.weatherapp.di

import com.example.weatherapp.data.api.APIDetails.BASE_URL
import com.example.weatherapp.data.api.APIEndPoints
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class WeatherModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    @Singleton
    fun provideWeatherService(retrofit: Retrofit): APIEndPoints {
        return retrofit.create(APIEndPoints::class.java)
    }
}