package com.example.weatherapp

/**
 * Data class for the current weather forecast data returned by Open Meteo API
 *
 * @property latitude Coordinate of the forecast location, may be null.
 * @property longitude Coordinate of the forecast location, may be null.
 * @property current_weather Current weather in the forecast location, may be null.
 * @property hourly Hourly forecast data of the location, specifically humidity, may be null.
 * @property daily Daily forecast data of the location, specifically sunrise and sunset, may be null.
 */
data class CurrentForecast (val latitude: Double?,
                            val longitude: Double?,
                            val current_weather: CurrentWeather?,
                            val hourly: HourlyHumidity?,
                            val daily: SunData?
)

/**
 * Data class which contains current weather data such as temperature, wind speed, weathercode
 * and the current time.
 *
 * @property temperature Current temperature in Celsius, may be null.
 * @property windspeed Current wind speed in km/h, may be null.
 * @property weathercode Weather code referencing the current weather condition, may be null.
 * @property time The current time in ISO format, may be null.
 */
data class CurrentWeather (val temperature: Double?,
                           val windspeed: Double?,
                           val weathercode: Int?,
                           val time: String?
)

/**
 * Data class for hourly humidity data for current weather.
 *
 * @property time List of time strings in ISO format, may be null.
 * @property relative_humidity_2m List of humidity values by the hour as percentages, may be null.
 */
data class HourlyHumidity(
    val time: List<String>?,
    val relative_humidity_2m: List<Double>?
)

/**
 * Data class for sunrise and sunset times.
 *
 * @property sunrise List of sunrise times in ISO format, may be null.
 * @property sunset List of sunset times in ISO format, may be null.
 */
data class SunData(
    val sunrise: List<String>?,
    val sunset: List<String>?
)

/**
 * Data class for 7 day forecast data returned by the Open Meteo API.
 *
 * @property latitude Coordinate of the forecast location, may be null.
 * @property longitude Coordinate of the forecast location, may be null.
 * @property daily Weather data for each of the 7 forecast days, may be null.
 */
data class WeekForecast (
    val latitude: Double?,
    val longitude: Double?,
    val daily: DailyData?
)

/**
 * Data class for the weather data of the 7 day forecast. Includes forecast data for
 * max and min temperatures, wind speed, sunrise and sunset times, humidity and weather codes.
 *
 * @property temperature_2m_max List of max temperatures in Celsius for each forecast day,
 *                              may be null.
 * @property temperature_2m_min List of min temperatures in Celsius for each forecast day,
 *                              may be null.
 * @property windspeed_10m_max List of wind speeds in km/h for each forecast day,
 *                              may be null.
 * @property sunrise List of sunrise times in ISO format, may be null.
 * @property sunset List of sunset times in ISO format, may be null.
 * @property relative_humidity_2m_max List of humidity values for each forecast day as percentages,
 *                              may be null.
 * @property weathercode List of weather codes referencing the weather condition,
 *                       may be null.
 */
data class DailyData(
    val temperature_2m_max: List<Double>?,
    val temperature_2m_min: List<Double>?,
    val windspeed_10m_max: List<Double>?,
    val sunrise: List<String>?,
    val sunset: List<String>?,
    val relative_humidity_2m_max: List<Double>?,
    val weathercode: List<Int>?
)

/**
 * Data class to which each day of the 7 day forecast is formatted to. Includes forecast data for
 * max and min temperatures, wind speed, sunrise and sunset times, humidity and weather condition
 * as well as which weekday is in question.
 *
 * @property temperatureMax Max temperature of the day in Celsius.
 * @property temperatureMin Min temperature of the day in Celsius.
 * @property windspeed Wind speed of the forecast day in km/h.
 * @property sunrise Sunrise time of the day in ISO format.
 * @property sunset Sunset time of the day in ISO format.
 * @property humidity Humidity value of the forecast day as a percentage.
 * @property weekDay The name of the weekday to which the forecast day corresponds to.
 * @property weatherCondition The weather condition of the day.
 */
data class DayForecast(
    val temperatureMax: Double,
    val temperatureMin: Double,
    val windspeed: Double,
    val sunrise: String,
    val sunset: String,
    val humidity: Double,
    val weekDay: String,
    val weatherCondition: WeatherCondition
)