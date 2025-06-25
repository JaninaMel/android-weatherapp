package com.example.weatherapp

/**
 * Data class for the geocoding response from the OpenWeatherMap's Geocoding API,
 * which includes the coordinates and city name.
 *
 * @property name Name of the city, may be null.
 * @property lat Coordinates of the location (latitude), may be null.
 * @property lon Coordinates of the location (longitude), may be null.
 */
data class GeoCodeResponse(
    val name: String?,
    val lat: Double?,
    val lon: Double?
)
