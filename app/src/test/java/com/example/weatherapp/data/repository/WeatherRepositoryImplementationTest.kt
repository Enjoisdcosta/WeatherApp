import com.example.weatherapp.data.api.APIEndPoints
import com.example.weatherapp.data.model.*
import com.example.weatherapp.data.repository.WeatherRepositoryImplementation
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class WeatherRepositoryTest {

    private lateinit var weatherService: APIEndPoints
    private lateinit var weatherRepository: WeatherRepositoryImplementation

    @Before
    fun setUp() {
        weatherService = mock(APIEndPoints::class.java)

        weatherRepository = WeatherRepositoryImplementation(weatherService)
    }

    @Test
    fun `test getWeatherByCity returns correct data`() = runBlocking {
        val mockWeather = Weather(
            base = "stations",
            clouds = Clouds(20),
            cod = 200,
            coord = Coord(44.34, 10.99),
            dt = 1633024800,
            id = 123456,
            main = Main(15.0, 1012.0, 60.0, 1000.0, 1013, 20, 16.0, 12.0),
            name = "Test City",
            sys = Sys("US", 1, 1633010400, 1633053600, 1),
            timezone = 7200,
            visibility = 10000,
            weather = listOf(WeatherX("light rain", "10d", 500, "Rain")),
            wind = Wind(deg = 200, gust = 7.0, speed = 5.0)  // Updated Wind class
        )

        `when`(weatherService.getWeatherByCity("Test City", "42def148c9bc0052a33b1e2a256bfc68")).thenReturn(mockWeather)

        val result = weatherRepository.getWeatherByCity("Test City", "42def148c9bc0052a33b1e2a256bfc68")

        assert(result.name == "Test City")
        assert(result.main.temp == 20)
        assert(result.main.feels_like == 15.0)
        assert(result.main.pressure == 1000.0)
        assert(result.weather[0].description == "light rain")
        assert(result.sys.country == "US")
        assert(result.sys.sunrise == 1633010400)
        assert(result.wind.speed == 5.0)
        assert(result.wind.gust == 7.0)
        assert(result.wind.deg == 200)
    }
}
