package com.example.myweatherapp.features.weather.data.entities.weatherresponse

import com.google.gson.annotations.SerializedName

data class ParentCityResponse(
    @SerializedName("latt_long")
    val lattLong: String,
    @SerializedName("location_type")
    val locationType: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("woeid")
    val woeId: Int
)