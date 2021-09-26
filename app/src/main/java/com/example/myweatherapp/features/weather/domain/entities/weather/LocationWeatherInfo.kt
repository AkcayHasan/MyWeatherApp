package com.example.myweatherapp.features.weather.domain.entities.weather

data class LocationWeatherInfo(
    val consolidatedWeather: List<ConsolidatedWeather>,
    val time: String,
    val sunRise: String,
    val sunSet: String,
    val timeZone: String
)
