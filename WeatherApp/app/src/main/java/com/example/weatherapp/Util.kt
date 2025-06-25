package com.example.weatherapp

import android.util.Log
import org.threeten.bp.LocalDate
import org.threeten.bp.format.TextStyle
import java.util.Locale

/**
 * Splits and returns the time of day from an ISO format string.
 *
 * If the given ISO string is null returns a default value string "Unavailable".
 *
 * @param time The ISO format time string to be split.
 * @return String which contains only the time of day from the time string.
 */
fun getTimeOfDay(time: String?): String {
    return time?.split("T")?.getOrNull(1) ?: "Unavailable"
}

/**
 * Formats [DailyData] objects into [DayForecast] objects in a list.
 *
 * Creates a 7 day forecast list of [DayForecast] objects from a [DailyData] object
 * and maps an appropriate weekday in accordance to the date of the forecast.
 *
 * If a data point is missing at an index, numeric values are defaulted to [Double.NaN],
 * and nullable Strings are handled accordingly in the functions they are invoked in.
 *
 * @return A list of [DayForecast] objects with weather details for the next 7 days.
 */
fun DailyData.formatToDayForecast(): List<DayForecast> {
    return (0 until 7).map { index ->
        DayForecast(
            temperatureMax = temperature_2m_max?.getOrNull(index) ?: Double.NaN,
            temperatureMin = temperature_2m_min?.getOrNull(index) ?: Double.NaN,
            windspeed = windspeed_10m_max?.getOrNull(index) ?:Double.NaN,
            sunrise = getTimeOfDay(sunrise?.getOrNull(index)),
            sunset = getTimeOfDay(sunset?.getOrNull(index)),
            humidity = relative_humidity_2m_max?.getOrNull(index) ?: Double.NaN,
            weekDay = if (index == 0 ) "Today" else getWeekDay(sunrise?.getOrNull(index)),
            weatherCondition = getWeatherByCode(weathercode?.getOrNull(index))
        )
    }
}

// Used AI help to get threetenabp to work because of
// API level issues I had using java.time
/**
 * Returns a weekday based on the given ISO format String including date information.
 *
 * Gets date out of the given ISO string and fetches a corresponding weekday name to that date.
 *
 * If an error occurs or the time string is null returns a default string "Unavailable".
 *
 * @param time The string containing the time data in ISO format, can be null.
 * @return A String with the weekday name in question or "Unavailable" if an error occurs.
 */
fun getWeekDay(time: String?): String {
    if (time != null) {
        val date = time.split("T").getOrNull(0) ?: return "Unavailable"

        return try {
            LocalDate.parse(date).dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH)
        } catch (e: Exception) {
            Log.e("getWeekDay", "Failed parsing date in getWeekDay()", e)
            "Unavailable"
        }
    } else {
        return "Unavailable"
    }
}