package com.example.myweatherapp.features.weather.domain.repository

import com.example.myweatherapp.features.weather.data.entities.locationresponse.NearLocationsResponse
import com.example.myweatherapp.features.weather.data.entities.weatherresponse.LocationWeatherResponse
import com.example.myweatherapp.util.Resource

interface IWeatherRepository {

    suspend fun getNearLocations(
        searchText: String?,
        lattLong: String?
    ): Resource<List<NearLocationsResponse>>

    suspend fun getLocationWeatherInfo(woeId: Long): Resource<LocationWeatherResponse>
}