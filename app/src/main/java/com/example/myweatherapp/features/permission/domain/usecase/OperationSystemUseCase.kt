package com.example.myweatherapp.features.permission.domain.usecase

import android.location.Location
import com.example.myweatherapp.features.permission.domain.repository.OperationSystemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class OperationSystemUseCase @Inject constructor(
    private val operationSystemRepository: OperationSystemRepository
) {

    suspend fun getUserLocation(
    ): Flow<Location> {
        return flow {
            try {
                operationSystemRepository.getLastKnowLocation()?.let {
                    emit(it)
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }.flowOn(Dispatchers.IO)
    }

}