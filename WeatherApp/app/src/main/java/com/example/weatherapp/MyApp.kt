package com.example.weatherapp

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

// Used AI help to get threetenabp to work because of
// API level issues I had using java.time
/**
 * Custom [Application] class for initializing libraries.
 *
 * Initializes AndroidThreeTen library.
 */
class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}