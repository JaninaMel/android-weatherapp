package com.example.weatherapp

import android.util.Log
import retrofit2.HttpException
import java.io.IOException

/**
 * Suspend function that retrieves the current weather forecast for the given location
 * using Open Meteo API.
 *
 * A network request is performed to fetch the current weather
 * and the function may throw or catch exceptions internally.
 * Null is returned in the case that the request fails.
 *
 * @param latitude Coordinate of the forecast location.
 * @param longitude Coordinate of the forecast location.
 * @return [CurrentForecast] object if request is successful, other wise null in case of an error.
 */
suspend fun getCurrentWeather(latitude: Double, longitude: Double): CurrentForecast? {
    try {
        val currentWeather = RetroFitClient.service.getCurrentForecast(
                                latitude,
                                longitude,
                                currentWeather = true
                            )
        return currentWeather
    } catch (e: IOException) {
        Log.e("HttpRequests", "Network error in getCurrentWeather()", e)
        return null
    } catch (e: HttpException) {
        Log.e("HttpRequests", "HTTP error in getCurrentWeather()", e)
        return null
    } catch (e: Exception) {
        Log.e("HttpRequests", "Unexpected error in getCurrentWeather()", e)
        return null
    }
}

/**
 * Suspend function that retrieves the 7 day forecast for the given location
 * using Open Meteo API.
 *
 * A network request is performed to fetch the 7 day forecast
 * and the function may throw or catch exceptions internally.
 * Null is returned in the case that the request fails.
 *
 * @param latitude Coordinate of the forecast location.
 * @param longitude Coordinate of the forecast location.
 * @return [WeekForecast] object if request is successful, other wise null in case of an error.
 */
suspend fun getWeekWeather(latitude: Double, longitude: Double): WeekForecast? {
    try {
        val weekForecast = RetroFitClient.service.getWeekForecast(latitude, longitude)
        return weekForecast
    } catch (e: IOException) {
        Log.e("HttpRequests", "Network error in getWeekWeather()", e)
        return null
    } catch (e: HttpException) {
        Log.e("HttpRequests", "HTTP error in getWeekWeather()", e)
        return null
    } catch (e: Exception) {
        Log.e("HttpRequests", "Unexpected error in getWeekWeather()", e)
        return null
    }
}

/**
 * Suspend function that retrieves city coordinates
 * by city name using OpenWeatherMap's Geocoding API.
 *
 * A network request is performed to fetch coordinates
 * and the function may throw or catch exceptions internally.
 * Null is returned in the case that the request fails.
 *
 * @param city The city name for which to fetch coordinates.
 * @param apiKey Unique API key for making requests into the API.
 * @return A list of [GeoCodeResponse] objects or null.
 */
suspend fun getCoordinatesByCity(
        city: String,
        apiKey: String = "f1eff760ddf6681f9683c4aaa508d3a9"
    ): List<GeoCodeResponse>? {
    try {
        val geoCodeResult = RetroFitClient.geocodeService.getCoordinates(city, limit = 1, apiKey)
        return geoCodeResult
    } catch (e: IOException) {
        Log.e("HttpRequests", "Network error in getCoordinatesByCity()", e)
        return null
    } catch (e: HttpException) {
        Log.e("HttpRequests", "HTTP error in getCoordinatesByCity()", e)
        return null
    } catch (e: Exception) {
        Log.e("HttpRequests", "Unexpected error in getCoordinatesByCity()", e)
        return null
    }
}