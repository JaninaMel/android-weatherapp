package com.example.weatherapp

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit API service interface for requesting forecast data from Open Meteo API.
 */
interface ApiService {
    /**
     * Retrieves the current weather forecast of a given location.
     *
     * @param latitude Coordinate of the forecast location.
     * @param longitude Coordinate of the forecast location.
     * @param currentWeather Boolean value for wheter or not
     *                       to include current weather data in the response.
     * @param hourly Hourly weather variables in the request, by default "relative_humidity_2m".
     * @param daily Daily weather variables in the request, by default "sunrise,sunset".
     * @param timezone The timezone for the the timestamps in the response, by default "auto".
     * @return [CurrentForecast] object that contains the current weather forecast data.
     */
    @GET("v1/forecast")
    suspend fun getCurrentForecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current_weather") currentWeather: Boolean,
        @Query("hourly") hourly: String = "relative_humidity_2m",
        @Query("daily") daily: String = "sunrise,sunset",
        @Query("timezone") timezone: String = "auto"
    ): CurrentForecast

    /**
     * Retrieves the 7 day forecast of a given location.
     *
     * @param latitude Coordinate of the forecast location.
     * @param longitude Coordinate of the forecast location.
     * @param daily The daily weather variables in the request,
     *              includes by default: max and min temperatures,
     *              wind speed, humidity, sunrise and sunset times
     *              and weather codes.
     * @param timezone The timezone for the the timestamps in the response, by default "auto".
     * @return [WeekForecast] object that contains the daily forecast data for the 7 day forecast.
     */
    @GET("v1/forecast")
    suspend fun getWeekForecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("daily") daily: String = "temperature_2m_max,temperature_2m_min,windspeed_10m_max,sunrise,sunset,relative_humidity_2m_max,weathercode",
        @Query("timezone") timezone: String = "auto"
    ): WeekForecast
}
