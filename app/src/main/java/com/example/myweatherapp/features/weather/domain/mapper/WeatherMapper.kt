package com.example.myweatherapp.features.weather.domain.mapper

import com.example.myweatherapp.features.weather.data.entities.locationresponse.NearLocationsResponse
import com.example.myweatherapp.features.weather.data.entities.weatherresponse.ConsolidatedWeatherResponse
import com.example.myweatherapp.features.weather.data.entities.weatherresponse.LocationWeatherResponse
import com.example.myweatherapp.features.weather.domain.entities.location.NearLocations
import com.example.myweatherapp.features.weather.domain.entities.weather.ConsolidatedWeather
import com.example.myweatherapp.features.weather.domain.entities.weather.LocationWeatherInfo

fun NearLocationsResponse.toNearLocations() = NearLocations(
    this.title, this.woeId
)

fun LocationWeatherResponse.toLocationWeatherInfo() = LocationWeatherInfo(
    this.consolidatedWeather.map {
        it.toConsolidatedWeather()
    },
    this.time,
    this.sunRise,
    this.sunSet,
    this.timezone
)

fun ConsolidatedWeatherResponse.toConsolidatedWeather() = ConsolidatedWeather(
    this.airPressure,
    this.applicableDate,
    this.humidity,
    this.maxTemp,
    this.minTemp,
    this.theTemp,
    this.visibility,
    this.weatherStateAbbr,
    this.weatherStateName,
    this.windSpeed
)