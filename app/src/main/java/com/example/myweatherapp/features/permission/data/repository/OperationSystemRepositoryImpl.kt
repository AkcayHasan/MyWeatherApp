package com.example.myweatherapp.features.permission.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.example.myweatherapp.features.permission.domain.repository.OperationSystemRepository
import com.example.myweatherapp.base.BaseServiceImpl
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.tasks.await

class OperationSystemRepositoryImpl(private val context: Context) :
    BaseServiceImpl(),
    OperationSystemRepository {

    @SuppressLint("MissingPermission")
    override suspend fun getLastKnowLocation(): Location =
        LocationServices.getFusedLocationProviderClient(context).lastLocation.await()
}