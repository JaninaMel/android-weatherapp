package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.ui.theme.WeatherAppTheme

/**
 * Main entry point of the app.
 *
 * Hosts the [WeatherApp] composable.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding)
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        WeatherApp()
                    }
                }
            }
        }
    }
}

/**
 * Composable hosting the main navigation structure of the app.
 *
 * Initializes the navigation controller and the shared [DayForecastViewModel] as well as
 * the [WeatherAppViewModel].
 * Sets up the [NavHost] to manage navigation between different screens.
 *
 * In the navigation:
 *  "weatherdisplay" - Displays the current weather and 7 day forecast with [WeatherDisplay]
 *  "details" - Displays details weather details based on the selected 7 day forecast day with [DetailsDisplay]
 */
@Composable
fun WeatherApp() {
    val navController = rememberNavController()
    val dayForecastViewModel: DayForecastViewModel = viewModel()
    val weatherAppViewModel: WeatherAppViewModel = viewModel()

    NavHost(navController = navController, startDestination = "weatherdisplay") {
        composable("weatherdisplay") {
            WeatherDisplay(navController, dayForecastViewModel, weatherAppViewModel)
        }
        composable("details") {
            DetailsDisplay(dayForecastViewModel)
        }
    }
}
