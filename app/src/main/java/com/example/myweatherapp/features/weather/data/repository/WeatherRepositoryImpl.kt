package com.example.myweatherapp.features.weather.data.repository

import com.example.myweatherapp.base.BaseServiceImpl
import com.example.myweatherapp.features.weather.domain.repository.IWeatherRepository
import com.example.myweatherapp.features.weather.data.api.WeatherApi
import com.example.myweatherapp.features.weather.data.entities.locationresponse.NearLocationsResponse
import com.example.myweatherapp.features.weather.data.entities.weatherresponse.LocationWeatherResponse
import com.example.myweatherapp.util.Resource
import retrofit2.Retrofit
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val retrofit: Retrofit
) : IWeatherRepository, BaseServiceImpl() {

    private val weatherApi: WeatherApi by lazy {
        retrofit.create(WeatherApi::class.java)
    }

    override suspend fun getNearLocations(
        searchText: String?,
        lattLong: String?
    ): Resource<List<NearLocationsResponse>> {
        return getResponse(request = { weatherApi.getNearLocations(searchText, lattLong) })
    }

    override suspend fun getLocationWeatherInfo(woeId: Int): Resource<LocationWeatherResponse> {
        return getResponse(request = { weatherApi.getLocationWeather(woeId) })
    }


}