package com.example.myweatherapp.features.weather.domain.entities.weather

data class ConsolidatedWeather(
    val airPressure: Double,
    val applicableDate: String,
    val humidity: Int,
    val maxTemp: Double,
    val minTemp: Double,
    val theTemp: Double,
    val visibility: Double,
    val weatherStateAbbr: String,
    val weatherStateName: String,
    val windSpeed: Double
)