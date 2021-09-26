package com.example.myweatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweatherapp.features.weather.data.entities.weatherresponse.LocationWeatherResponse
import com.example.myweatherapp.features.weather.domain.usecases.DetailUseCase
import com.example.myweatherapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailUseCase: DetailUseCase
) : ViewModel() {

    private val locationWeatherInfo =
        MutableStateFlow<Resource<LocationWeatherResponse>>(Resource.success(null))
    val locationWeather: StateFlow<Resource<LocationWeatherResponse>>
        get() = locationWeatherInfo


    suspend fun getLocationWeatherInfo(woeId: Int) {
        locationWeatherInfo.value = Resource.loading(null)
        viewModelScope.launch {
            try {
                detailUseCase.getLocationWeatherInfo(woeId).collect {
                    locationWeatherInfo.value = it
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

    }


}