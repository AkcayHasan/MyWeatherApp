package com.example.myweatherapp.features.weather.domain.mapper

import com.example.myweatherapp.features.weather.data.entities.locationresponse.NearLocationsResponse
import com.example.myweatherapp.features.weather.domain.entities.NearLocations

fun NearLocationsResponse.toNearLocations() = NearLocations(
    this.title, this.woeId
)