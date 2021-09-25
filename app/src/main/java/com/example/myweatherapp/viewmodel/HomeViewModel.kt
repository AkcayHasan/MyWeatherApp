package com.example.myweatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweatherapp.features.weather.data.entities.locationresponse.NearLocationsResponse
import com.example.myweatherapp.features.weather.domain.usecases.HomeUseCase
import com.example.myweatherapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCase: HomeUseCase
) : ViewModel() {

    private val nearLocations =
        MutableStateFlow<Resource<List<NearLocationsResponse>>>(Resource.success(null))
    val listNearLocations: StateFlow<Resource<List<NearLocationsResponse>>>
        get() = nearLocations

    suspend fun getNearLocations(searchText: String?, lattLong: String?) {
        viewModelScope.launch {
            try {
                homeUseCase.getNearLocations(searchText, lattLong).collect {
                    nearLocations.value = it
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }


}