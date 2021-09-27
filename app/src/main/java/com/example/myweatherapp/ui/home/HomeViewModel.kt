package com.example.myweatherapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweatherapp.features.weather.data.entities.locationresponse.NearLocationsResponse
import com.example.myweatherapp.features.weather.domain.entities.location.NearLocations
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
        MutableStateFlow<Resource<List<NearLocations>>>(Resource.success(null))
    val listNearLocations: StateFlow<Resource<List<NearLocations>>>
        get() = nearLocations

    suspend fun getNearLocations(searchText: String?, lattLong: String?) {
        nearLocations.value = Resource.loading(null)
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