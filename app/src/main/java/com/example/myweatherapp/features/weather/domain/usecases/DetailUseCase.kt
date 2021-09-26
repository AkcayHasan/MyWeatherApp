package com.example.myweatherapp.features.weather.domain.usecases

import com.example.myweatherapp.features.weather.data.entities.weatherresponse.LocationWeatherResponse
import com.example.myweatherapp.features.weather.domain.repository.IWeatherRepository
import com.example.myweatherapp.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DetailUseCase @Inject constructor(
    private val weatherRepository: IWeatherRepository
) {

    suspend fun getLocationWeatherInfo(woeId: Int): Flow<Resource<LocationWeatherResponse>> {
        return flow {
            val result = weatherRepository.getLocationWeatherInfo(woeId)
            //emit(Resource.loading(null))
            emit(result)
        }.flowOn(Dispatchers.IO)
    }


}