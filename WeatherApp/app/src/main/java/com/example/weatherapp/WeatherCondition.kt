package com.example.weatherapp

/**
 * Data class for weather conditions with a description of the weather and
 * an icon.
 *
 * @property weatherTitle The description of the weather condition.
 * @property iconId The ID of the icon from drawable that corresponds to the weather condition.
 */
data class WeatherCondition(
    val weatherTitle: String,
    val iconId: Int
)

/**
 * Returns [WeatherCondition] based on the given WMO Weather Condition Code.
 *
 * The function maps weather codes into a more readable weather condition description and
 * a corresponding icon. If the weather code is not recognized or is null the function returns
 * a default condition for unknown.
 *
 * @param weathercode The weather condition code, may be null.
 * @return [WeatherCondition] object with a weather description and icon.
 */
fun getWeatherByCode(weathercode: Int?): WeatherCondition {
    return when (weathercode) {
        0 -> WeatherCondition("Clear", R.drawable.clear)
        1, 2 -> WeatherCondition("Partly Cloudy", R.drawable.partly_cloudy)
        3 -> WeatherCondition("Overcast", R.drawable.overcast)
        45, 48 -> WeatherCondition("Foggy", R.drawable.foggy)
        51, 53, 55 -> WeatherCondition("Drizzle", R.drawable.drizzle)
        80, 81, 82 -> WeatherCondition("Showers", R.drawable.showers)
        61, 63, 65 -> WeatherCondition("Rain", R.drawable.rain)
        56, 57 -> WeatherCondition("Icy Drizzle", R.drawable.drizzle)
        66, 67 -> WeatherCondition("Icy Rain", R.drawable.rain)
        77 -> WeatherCondition("Snow Grains", R.drawable.snow)
        85, 86 -> WeatherCondition("Snow Showers", R.drawable.snow)
        71, 73, 75 -> WeatherCondition("Snow", R.drawable.snow)
        95, 96, 99 -> WeatherCondition("Thunder Storm", R.drawable.thunder)
        else -> WeatherCondition("Weather condition unknown.", R.drawable.error)
    }
}
