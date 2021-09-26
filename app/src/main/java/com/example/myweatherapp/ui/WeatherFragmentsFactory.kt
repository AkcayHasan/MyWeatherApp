package com.example.myweatherapp.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.example.myweatherapp.adapter.LocationsRecyclerViewAdapter
import javax.inject.Inject

class WeatherFragmentsFactory @Inject constructor(
    private val locationsRecyclerViewAdapter: LocationsRecyclerViewAdapter,
    private val glide: RequestManager
) : FragmentFactory(){
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            HomeFragment::class.java.name -> HomeFragment(locationsRecyclerViewAdapter)
            DetailFragment::class.java.name -> DetailFragment(glide)
            else -> super.instantiate(classLoader, className)
        }
    }
}