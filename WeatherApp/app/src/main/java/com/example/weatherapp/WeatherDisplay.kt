package com.example.weatherapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

/**
 * Composable for the main weather screen, includes both current weather data as well
 * as a 7 day forecast.
 *
 * Displays an alert if fetching location data fails.
 *
 * @param navController Navigation Controller for navigation to different screens.
 * @param dayForecastViewModel ViewModel shared between screens to hold selected forecast day data.
 * @param weatherAppViewModel ViewModel to hold location data to be used in fetching forecast data.
 */
@Composable
fun WeatherDisplay(navController: NavController,
                   dayForecastViewModel: DayForecastViewModel,
                   weatherAppViewModel: WeatherAppViewModel
                   ) {
    val latitude = weatherAppViewModel.latitude
    val longitude = weatherAppViewModel.longitude
    val alert = weatherAppViewModel.errorMessage

    Column {
        if (alert != null) {
            ErrorAlert(alert) { weatherAppViewModel.errorMessage = null }
        }
        CurrentWeatherDisplay(latitude, longitude, weatherAppViewModel)
        WeekForecastDisplay(latitude, longitude, navController, dayForecastViewModel)
    }
}

/**
 * Composable that displays the current weather of a given location.
 *
 * Fetches the current weather data and displays it in the UI.
 * If an issue occurs in fetching weather data displays an error message.
 *
 * @param latitude Coordinate of the forecast location.
 * @param longitude Coordinate of the forecast location.
 * @param weatherAppViewModel ViewModel to hold location data to be used in fetching forecast data.
 */
@Composable
fun CurrentWeatherDisplay(
    latitude: Double,
    longitude: Double,
    weatherAppViewModel: WeatherAppViewModel
) {
    var isLoading by remember { mutableStateOf(true) }
    var currentWeather by remember { mutableStateOf<CurrentForecast?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(latitude, longitude) {
        isLoading = true
        val response = getCurrentWeather(latitude, longitude)
        if (response != null) {
            currentWeather = response
            errorMessage = null // Ensure error message is null
        } else {
            errorMessage = "Failed to fetch weather data."
        }
        isLoading = false
    }

    var showSearch by remember { mutableStateOf(false) }
    var cityInput by remember { mutableStateOf("") }
    val buttonYellow = Color(0xFFE8C500)
    val city = weatherAppViewModel.city

    when {
        isLoading -> CircularProgressIndicator()
        errorMessage != null -> {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.error),
                    contentDescription = "Error Icon",
                    modifier = Modifier.size(128.dp)
                )
                Text(errorMessage!!, fontSize = 20.sp)
            }
        }
        currentWeather != null -> {
            val current_weather: CurrentWeather? = currentWeather!!.current_weather
            val windSpeed = current_weather?.windspeed?.div(3.6)

            val currentTime = current_weather?.time
            val timeIndex = if (currentTime != null && currentWeather!!.hourly?.time != null) {
                currentWeather!!.hourly!!.time!!.indexOfFirst { time ->
                    currentTime.startsWith(time.dropLast(2))
                }
            } else {
                -1
            }

            val currentHumidity: Double? = if (timeIndex != -1 && currentWeather!!.hourly?.relative_humidity_2m != null) {
                currentWeather!!.hourly!!.relative_humidity_2m!![timeIndex]
            } else {
                null
            }

            val sunrise = getTimeOfDay(currentWeather!!.daily?.sunrise?.getOrNull(0))
            val sunset = getTimeOfDay(currentWeather!!.daily?.sunset?.getOrNull(0))
            val weatherCondition = getWeatherByCode(current_weather?.weathercode)

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(140.dp)
                ){
                    Text("Sunrise: $sunrise", fontSize = 18.sp)
                    Text("Sunset: $sunset", fontSize = 18.sp)
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(city, fontSize = 30.sp, fontWeight = FontWeight.Light)
                    IconButton(onClick = { showSearch = !showSearch }) {
                        Icon(
                            painter = painterResource(R.drawable.search),
                            contentDescription = "City search",
                            modifier = Modifier.size(35.dp)
                        )
                    }
                }

                if (showSearch) {
                    Row {
                        TextField(
                            value = cityInput,
                            onValueChange = { cityInput = it },
                            label = { Text("Input city") }
                        )
                        Button(
                            onClick = {
                            if (cityInput.isNotBlank() ) {
                                weatherAppViewModel.selectCity(cityInput.trim())
                            }
                        },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = buttonYellow,
                                contentColor = Color.White
                            )
                        ) {
                            Text("Search", fontSize = 20.sp)
                        }
                    }
                }

                Image(
                    painter = painterResource(id = weatherCondition.iconId),
                    contentDescription = weatherCondition.weatherTitle,
                    modifier = Modifier.size(128.dp)
                )
                Text(
                    text = if (current_weather?.temperature != null)
                        "${current_weather.temperature}°C"
                     else
                        "Temperature Unavailable",
                    fontSize = 40.sp,
                    style = TextStyle(fontFamily = FontFamily.Serif)
                )
                Text(
                    weatherCondition.weatherTitle,
                    fontSize = 30.sp,
                    style = TextStyle(fontFamily = FontFamily.Serif),
                    fontWeight = FontWeight.Light
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(30.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row {
                            Text(
                                text = if (windSpeed != null)
                                    "${"%.1f".format(windSpeed)} m/s"
                                else
                                    "Unavailable"
                            )
                            Image(
                                painter = painterResource(id = R.drawable.wind_blue),
                                contentDescription = "Wind icon",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Text("Wind Speed")
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row {
                            Text(
                                text = if (currentHumidity != null)
                                    "${"%.1f".format(currentHumidity)}%"
                                else
                                    "Unavailable"
                            )
                            Image(
                                painter = painterResource(id = R.drawable.raindrop_blue),
                                contentDescription = "Raindrop icon",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Text("Humidity")
                    }
                }
            }
        }
    }
}

/**
 * Composable for displaying a 7 day forecast of a given location.
 *
 * Fetches weekly forecast data and displays it in the UI. If
 * an error occurs in fetching of the data, displays an error message.
 *
 * @param latitude Coordinate of the forecast location.
 * @param longitude Coordinate of the forecast location.
 * @param navController Navigation Controller for navigation to different screens.
 * @param dayForecastViewModel ViewModel shared between screens to hold selected forecast day data.
 */
@Composable
fun WeekForecastDisplay(
    latitude: Double,
    longitude: Double,
    navController: NavController,
    dayForecastViewModel: DayForecastViewModel
) {
    var isLoading by remember { mutableStateOf(true) }
    var weekForecast by remember { mutableStateOf<WeekForecast?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(latitude, longitude) {
        isLoading = true
        val response = getWeekWeather(latitude, longitude)
        if (response != null) {
            weekForecast = response
            errorMessage = null // Ensure error message is null
        } else {
            errorMessage = "Failed to fetch week's weather data."
        }
        isLoading = false
    }

    when {
        isLoading -> CircularProgressIndicator()
        errorMessage != null -> {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.error),
                    contentDescription = "Error Icon",
                    modifier = Modifier.size(128.dp)
                )
                Text(errorMessage!!, fontSize = 20.sp)
            }
        }
        weekForecast != null -> {
            val dailyWeather = weekForecast!!.daily
            val dayForecastList = dailyWeather?.formatToDayForecast()

            if (dayForecastList != null) {
                LazyColumn {
                    items(dayForecastList) { dayForecast ->
                        DayForecastDisplay(dayForecast, navController, dayForecastViewModel)
                    }
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Weekly forecast data is unavailable", fontSize = 20.sp)
                }
            }
        }
    }
}

/**
 * Composable for displaying summaries of individual days of the 7 day forecast.
 *
 * When clicked sets the selected forecast in the ViewModel and navigates to to
 * details screen.
 *
 * @param dayForecast [DayForecast] object representing a single day's forecast from the 7 day forecast.
 * @param navController Navigation Controller for navigation to different screens.
 * @param dayForecastViewModel ViewModel shared between screens to hold selected forecast day data.
 */
@Composable
fun DayForecastDisplay(
    dayForecast: DayForecast,
    navController: NavController,
    dayForecastViewModel: DayForecastViewModel
) {
    val windSpeed = dayForecast.windspeed
    val tempMax = if (dayForecast.temperatureMax.isNaN()) "Unavailable" else "${dayForecast.temperatureMax}°C"
    val tempMin = if (dayForecast.temperatureMin.isNaN()) "Unavailable" else "${dayForecast.temperatureMin}°C"

    Column(
        modifier = Modifier.padding(10.dp)
            .fillMaxWidth()
            .background(Color(0x661BA6E2), shape = RoundedCornerShape(16.dp))
            .clickable{
                dayForecastViewModel.selectDayForecast(dayForecast)
                navController.navigate("details")
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.padding(
                start = 16.dp,
                top = 30.dp,
                end = 16.dp,
                bottom = 30.dp)
        ) {
            Text(dayForecast.weekDay, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(
                text = "$tempMin/$tempMax",
                fontSize = 18.sp
            )
            Row {
                Text(
                    text = if (!windSpeed.isNaN())
                        "${"%.1f".format(windSpeed/ 3.6)} m/s"
                    else
                        "Unavailable",
                    fontSize = 18.sp
                )
                Image(
                    painter = painterResource(id = R.drawable.wind_blue),
                    contentDescription = "Wind icon",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

/**
 * A popup alert composable that is shown when an error occurs.
 *
 * @param errorMessage The error message to be displayed.
 * @param onDismiss A callback function triggered when the alert is dismissed.
 */
@Composable
fun ErrorAlert(
    errorMessage: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Something went wrong") },
        text = { Text(errorMessage) },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("OK")
            }
        }
    )
}