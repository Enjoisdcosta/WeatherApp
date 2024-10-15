package com.example.weatherapp.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherapp.data.model.*
import com.example.weatherapp.data.repository.WeatherRepositoryImplementation
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class WeatherViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var weatherRepository: WeatherRepositoryImplementation
    private lateinit var viewModel: WeatherViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        weatherRepository = mock(WeatherRepositoryImplementation::class.java)

        viewModel = WeatherViewModel(weatherRepository)
    }

    @Test
    fun `test fetchWeatherByCity updates weatherResponse`() = runTest {
        val mockWeather = Weather(
            base = "stations",
            clouds = Clouds(20),
            cod = 200,
            coord = Coord(44.34, 10.99),
            dt = 1633024800,
            id = 123456,
            main = Main(
                feels_like = 15.0,
                grnd_level = 1012.0,
                humidity = 60.0,
                pressure = 1000.0,
                sea_level = 1013,
                temp = 20,
                temp_max = 16.0,
                temp_min = 12.0
            ),
            name = "Test City",
            sys = Sys(
                country = "US",
                id = 1,
                sunrise = 1633010400,
                sunset = 1633053600,
                type = 1
            ),
            timezone = 7200,
            visibility = 10000,
            weather = listOf(WeatherX(
                description = "light rain",
                icon = "10d",
                id = 500,
                main = "Rain"
            )),
            wind = Wind(deg = 200, gust = 7.0, speed = 5.0)
        )

        `when`(weatherRepository.getWeatherByCity("New York", "42def148c9bc0052a33b1e2a256bfc68")).thenReturn(mockWeather)

        viewModel.fetchWeatherByCity("New York", null, null)

        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("New York", viewModel.weatherResponse?.name)
        assertEquals(20, viewModel.weatherResponse?.main?.temp)
        assertEquals(15.0, viewModel.weatherResponse?.main?.feels_like)
        assertEquals(60.0, viewModel.weatherResponse?.main?.humidity)
        assertEquals("light rain", viewModel.weatherResponse?.weather?.get(0)?.description)
        assertEquals("Rain", viewModel.weatherResponse?.weather?.get(0)?.main)
        assertEquals("US", viewModel.weatherResponse?.sys?.country)
        assertEquals(7.0, viewModel.weatherResponse?.wind?.gust)
        assertEquals(200, viewModel.weatherResponse?.wind?.deg)
    }
}
