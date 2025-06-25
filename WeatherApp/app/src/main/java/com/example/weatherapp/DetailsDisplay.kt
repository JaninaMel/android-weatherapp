package com.example.weatherapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Displays weather details for the selected day from the WeatherDisplay.
 *
 * The details displayed in this composable include:
 * max and min temperatures, wind speed, humidity,
 * sunrise and sunset times and the weather condition of the day.
 *
 * The DayForecast used in the composable is retrieved from the DayForecastViewModel.
 * If a forecast has not been selected (meaning the DayForecast received is null),
 * an error message is displayed instead.
 *
 * @param dayForecastViewModel The [DayForecastViewModel] which provides the selected day's forecast.
 */
@Composable
fun DetailsDisplay(dayForecastViewModel: DayForecastViewModel) {
    val dayForecast = dayForecastViewModel.getDayForecast()

    if (dayForecast != null) {
        val windSpeed = dayForecast.windspeed
        val tempMax = if (dayForecast.temperatureMax.isNaN()) "Unavailable" else "${dayForecast.temperatureMax}°C"
        val tempMin = if (dayForecast.temperatureMin.isNaN()) "Unavailable" else "${dayForecast.temperatureMin}°C"

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                dayForecast.weekDay,
                fontSize = 30.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier.padding(top = 30.dp)
            )

            Image(
                painter = painterResource(id = dayForecast.weatherCondition.iconId),
                contentDescription = dayForecast.weatherCondition.weatherTitle,
                modifier = Modifier.size(140.dp)
            )

            Text(
                dayForecast.weatherCondition.weatherTitle,
                fontSize = 30.sp,
                style = TextStyle(fontFamily = FontFamily.Serif),
                fontWeight = FontWeight.Light,
                modifier = Modifier.padding(bottom = 16.dp)
            )


            Row {
                Column(
                    modifier = Modifier.padding(10.dp)
                        .size(170.dp)
                        .background(Color(0x661BA6E2), shape = RoundedCornerShape(16.dp)),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Lowest", fontSize = 20.sp)
                    Text("Temperature:", fontSize = 20.sp)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(tempMin, fontSize = 24.sp)
                        Image(
                            painter = painterResource(id = R.drawable.temp_min),
                            contentDescription = "Low temperature icon",
                            modifier = Modifier.size(64.dp)
                        )
                    }
                }
                Column(
                    modifier = Modifier.padding(10.dp)
                        .size(170.dp)
                        .background(Color(0x661BA6E2), shape = RoundedCornerShape(16.dp)),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Highest", fontSize = 20.sp)
                    Text("Temperature:", fontSize = 20.sp)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(tempMax, fontSize = 24.sp)
                        Image(
                            painter = painterResource(id = R.drawable.temp_max),
                            contentDescription = "High temperature icon",
                            modifier = Modifier.size(64.dp)
                        )
                    }
                }
            }

            Row {
                Column(
                    modifier = Modifier.padding(10.dp)
                        .size(170.dp)
                        .background(Color(0x661BA6E2), shape = RoundedCornerShape(16.dp)),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Wind Speed:", fontSize = 20.sp)
                    Image(
                        painter = painterResource(id = R.drawable.wind_blue),
                        contentDescription = "Wind icon",
                        modifier = Modifier.size(64.dp)
                    )
                    Text(
                        text = if (!windSpeed.isNaN())
                            "${"%.1f".format(windSpeed/ 3.6)} m/s"
                        else
                            "Unavailable",
                        fontSize = 24.sp
                    )
                }
                Column(
                    modifier = Modifier.padding(10.dp)
                        .size(170.dp)
                        .background(Color(0x661BA6E2), shape = RoundedCornerShape(16.dp)),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Humidity:", fontSize = 20.sp)
                    Image(
                        painter = painterResource(id = R.drawable.raindrop_blue),
                        contentDescription = "Raindrop icon",
                        modifier = Modifier.size(64.dp)
                    )
                    Text(
                        text = if (!dayForecast.humidity.isNaN())
                            "${dayForecast.humidity} %"
                        else
                            "Unavailable",
                        fontSize = 24.sp)
                }
            }

            Row {
                Column(
                    modifier = Modifier.padding(10.dp)
                        .size(170.dp)
                        .background(Color(0x661BA6E2), shape = RoundedCornerShape(16.dp)),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Sunrise:", fontSize = 20.sp)
                    Image(
                        painter = painterResource(id = R.drawable.sunrise),
                        contentDescription = "Sunrise icon",
                        modifier = Modifier.size(64.dp)
                    )
                    Text(dayForecast.sunrise, fontSize = 24.sp)
                }
                Column(
                    modifier = Modifier.padding(10.dp)
                        .size(170.dp)
                        .background(Color(0x661BA6E2), shape = RoundedCornerShape(16.dp)),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Sunset:", fontSize = 20.sp)
                    Image(
                        painter = painterResource(id = R.drawable.sunset),
                        contentDescription = "Sunset icon",
                        modifier = Modifier.size(64.dp)
                    )
                    Text(dayForecast.sunset, fontSize = 24.sp)
                }
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.error),
                contentDescription = "Error icon",
                modifier = Modifier.size(128.dp)
            )
            Text("An error has occurred trying to access weather details.")
        }
    }
}