package com.example.myweatherapp.features.weather.domain.usecases

import com.example.myweatherapp.features.weather.domain.entities.location.NearLocations
import com.example.myweatherapp.features.weather.domain.mapper.toNearLocations
import com.example.myweatherapp.features.weather.domain.repository.IWeatherRepository
import com.example.myweatherapp.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HomeUseCase @Inject constructor(
    private val weatherRepository: IWeatherRepository
) {

    suspend fun getNearLocations(
        searchText: String?,
        lattLong: String?
    ): Flow<Resource<List<NearLocations>>> {
        return flow {
            emit(Resource.loading(null))
            val result = weatherRepository.getNearLocations(searchText, lattLong)
            result.data?.let {
                emit(Resource.success(it.map { response ->
                    response.toNearLocations()
                }))
            } ?: kotlin.run {
                emit(Resource.error(result.message ?: "", null))
            }
        }.flowOn(Dispatchers.IO)
    }


}