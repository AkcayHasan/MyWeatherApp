package com.example.myweatherapp.features.weather.data.repository

import com.example.myweatherapp.features.weather.domain.repository.IWeatherRepository
import com.example.myweatherapp.features.weather.data.api.WeatherApi
import com.example.myweatherapp.features.weather.data.entities.locationresponse.NearLocationsResponse
import com.example.myweatherapp.features.weather.data.entities.weatherresponse.LocationWeatherResponse
import com.example.myweatherapp.util.Resource
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val retrofit: WeatherApi
) : IWeatherRepository {

    override suspend fun getNearLocations(
        searchText: String?,
        lattLong: String?
    ): Resource<List<NearLocationsResponse>> {
        return try {
            val locationResponse = retrofit.getNearLocations(searchText, lattLong)
            if (locationResponse.isSuccessful) {
                locationResponse.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("An Error Occured!", null)
            } else {
                Resource.error("An Error Occured!", null)
            }

        } catch (e: Exception) {
            Resource.error("No Data!", null)
        }
    }

    override suspend fun getLocationWeatherInfo(woeId: Int): Resource<LocationWeatherResponse> {
        return try {
            val weatherResponse = retrofit.getLocationWeather(woeId)
            if (weatherResponse.isSuccessful) {
                weatherResponse.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("An Error Occured!", null)
            } else {
                Resource.error("An Error Occured!", null)
            }

        } catch (e: Exception) {
            Resource.error("No Data!", null)
        }
    }


}