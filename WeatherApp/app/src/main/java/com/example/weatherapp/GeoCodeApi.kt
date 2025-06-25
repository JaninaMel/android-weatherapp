package com.example.weatherapp

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit API service interface for requesting location data from OpenWeatherMap's Geocoding API.
 */
interface GeoCodeApi {
    /**
     * Retrieves the location data of a given city.
     *
     * @param city The city name for which to fetch coordinates.
     * @param limit Number of locations in the API response.
     * @param apiKey Unique API key for making requests into the API.
     * @return A list of [GeoCodeResponse] objects.
     */
    @GET("geo/1.0/direct")
    suspend fun getCoordinates(
        @Query("q") city: String,
        @Query("limit") limit: Int,
        @Query("appid") apiKey: String
    ): List<GeoCodeResponse>
}