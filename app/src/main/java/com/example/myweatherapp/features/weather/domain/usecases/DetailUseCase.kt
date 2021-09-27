package com.example.myweatherapp.features.weather.domain.usecases

import com.example.myweatherapp.features.weather.domain.entities.weather.LocationWeatherInfo
import com.example.myweatherapp.features.weather.domain.mapper.toLocationWeatherInfo
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

    suspend fun getLocationWeatherInfo(woeId: Int): Flow<Resource<LocationWeatherInfo>> {
        return flow {
            emit(Resource.loading(null))
            val result = weatherRepository.getLocationWeatherInfo(woeId)
            result.data?.let {
                emit( Resource.success(
                    it.toLocationWeatherInfo()
                ))
            } ?: kotlin.run {
                emit(Resource.error(result.message?:"",null))
            }
        }.flowOn(Dispatchers.IO)
    }


}