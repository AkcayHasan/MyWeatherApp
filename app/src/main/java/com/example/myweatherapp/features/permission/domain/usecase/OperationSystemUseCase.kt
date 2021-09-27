package com.example.myweatherapp.features.permission.domain.usecase

import android.location.Location
import android.util.Log
import com.example.myweatherapp.features.permission.domain.repository.OperationSystemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class OperationSystemUseCase @Inject constructor(
    private val operationsystemRepository: OperationSystemRepository) {

    suspend fun getUserLocation(
    ): Flow<Location> {
        return flow {
            try {
                operationsystemRepository.getLastKnowLocation()?.let {
                    emit(it)
                }
            }catch (ex:Exception){
                Log.d("ve","d")
            }
        }.flowOn(Dispatchers.IO)
    }

}