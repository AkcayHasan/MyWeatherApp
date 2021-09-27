package com.example.myweatherapp.features.permission.domain.repository

import android.location.Location

interface OperationSystemRepository{
   suspend fun getLastKnowLocation() : Location
}