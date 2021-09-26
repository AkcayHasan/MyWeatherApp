package com.example.myweatherapp.features.weather.data.api

import com.example.myweatherapp.features.weather.data.entities.weatherresponse.LocationWeatherResponse
import com.example.myweatherapp.features.weather.data.entities.locationresponse.NearLocationsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApi {

    @GET("location/search/")
    suspend fun getNearLocations(
        @Query("query") searchCityText: String?,
        @Query("lattlong") lattLong: String?
    ): Response<List<NearLocationsResponse>>

    @GET("location/{woeid}/")
    suspend fun getLocationWeather(
        @Path("woeid") woeId: Int
    ): Response<LocationWeatherResponse>

}