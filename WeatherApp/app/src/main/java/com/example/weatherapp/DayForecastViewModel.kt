package com.example.weatherapp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

/**
 * ViewModel for passing a selected [DayForecast] between composables.
 * The ViewModel passes a selected day's forecast from [WeatherDisplay] to [DetailsDisplay].
 */
class DayForecastViewModel: ViewModel() {
    private var selectedDayForecast by mutableStateOf<DayForecast?>(null)

    /**
     * Setter for the selected day forecast.
     *
     * @param dayForecast The [DayForecast] to be set.
     */
    fun selectDayForecast(dayForecast: DayForecast) {
        selectedDayForecast = dayForecast
    }

    /**
     * Getter for the selected day forecast.
     *
     * @return The selected [DayForecast] or null if a day forecast has not been selected.
     */
    fun getDayForecast(): DayForecast? {
        return selectedDayForecast
    }
}