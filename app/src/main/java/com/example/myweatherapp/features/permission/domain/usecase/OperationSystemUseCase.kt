package com.example.myweatherapp.features.permission.domain.usecase

import android.location.Location
import com.example.myweatherapp.base.CoreNetworkError
import com.example.myweatherapp.features.permission.domain.repository.OperationSystemRepository
import com.example.myweatherapp.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class OperationSystemUseCase @Inject constructor(
    private val operationSystemRepository: OperationSystemRepository
) {

    suspend fun getUserLocation(
    ): Flow<Resource<Location>> {
        return flow {
            try {
                emit(Resource.success(operationSystemRepository.getLastKnowLocation()))
            } catch (ex: Exception) {
                Resource.error(CoreNetworkError(ex).appErrorMessage ?: "", null)
            }
        }.flowOn(Dispatchers.IO)
    }

}