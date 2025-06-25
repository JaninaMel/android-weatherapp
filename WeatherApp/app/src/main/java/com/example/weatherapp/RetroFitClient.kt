package com.example.weatherapp

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * An object that provides Retrofit client for making requests to the Open Meteo API and the
 * OpenWeatherMap's Geocoding API.
 *
 * Provides non-default timeouts and uses [GsonConverterFactory] to handle JSON data.
 * The base URL for Open Meteo: "https://api.open-meteo.com/"
 * The base URL for OpenWeatherMap: "https://api.openweathermap.org/"
 *
 * @property service Instance of [ApiService] for making forecast API calls to Open Meteo.
 * @property geocodeService Instance of [GeoCodeApi] for making geocoding API calls to OpenWeatherMap.
 */
object RetroFitClient {
    private const val METEO_URL = "https://api.open-meteo.com/"
    private  const val GEOCODE_URL = "https://api.openweathermap.org/"

    private val client =  OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    // Open Meteo
    val service: ApiService = Retrofit.Builder()
        .baseUrl(METEO_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    // Open Weather Map (Geocoding)
    val geocodeService: GeoCodeApi = Retrofit.Builder()
        .baseUrl(GEOCODE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GeoCodeApi::class.java)
}