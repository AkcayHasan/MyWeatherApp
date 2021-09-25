package com.example.myweatherapp.features.weather.domain.usecases

import com.example.myweatherapp.features.weather.data.entities.locationresponse.NearLocationsResponse
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
    ): Flow<Resource<List<NearLocationsResponse>>> {
        return flow {
            val result = weatherRepository.getNearLocations(searchText, lattLong)
            emit(Resource.loading(null))
            emit(result)
        }.flowOn(Dispatchers.IO)
    }


}