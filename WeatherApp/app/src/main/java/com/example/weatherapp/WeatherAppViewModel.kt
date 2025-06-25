package com.example.weatherapp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * VievModel for holding location information.
 *
 * The ViewModel has states for city name and coordinates which are used to fetch weather
 * forecast data.
 *
 * The viewModel also has an errorMessage state used to communicate issues in
 * fetching data.
 */
class WeatherAppViewModel: ViewModel() {
    /**
     * Name of the currently selected city.
     */
    var city by mutableStateOf("Tampere")
        private set

    /**
     * Latitude of the selected city.
     */
    var latitude by mutableDoubleStateOf(61.49)
        private set

    /**
     * Longitude of the selected city.
     */
    var longitude by mutableDoubleStateOf(23.78)
        private set

    /**
     * Possible error message should an error occur.
     */
    var errorMessage by mutableStateOf<String?>(null)

    /**
     * Updates the selected city and fetches it's coordinates.
     * If successful coordinates and city name are updated accordingly.
     * In case of failure, city reverts to it's previous value and an error message
     * is given.
     *
     * @param newCity The name of the city whose coordinates are to be fetched.
     */
    fun selectCity(newCity: String) {
        var prevCity = city
        city = newCity
        viewModelScope.launch {
            val response = getCoordinatesByCity(city)
            if (!response.isNullOrEmpty() && response[0].lat != null && response[0].lon != null) {
                latitude = response[0].lat!!
                longitude = response[0].lon!!
                if (response[0].name != null) {
                    city = response[0].name.toString()
                }
                prevCity = city
                errorMessage = null
            } else {
                errorMessage = "Failed to fetch location."
                city = prevCity
            }
        }
    }
}