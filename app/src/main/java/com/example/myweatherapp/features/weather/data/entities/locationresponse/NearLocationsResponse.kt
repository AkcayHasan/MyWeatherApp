package com.example.myweatherapp.features.weather.data.entities.locationresponse

import com.google.gson.annotations.SerializedName

data class NearLocationsResponse(
    @SerializedName("distance")
    val distance: Int?,
    @SerializedName("title")
    val title: String,
    @SerializedName("location_type")
    val locationType: String,
    @SerializedName("woeid")
    val woeId: Int,
    @SerializedName("latt_long")
    val lattLong: String
)
