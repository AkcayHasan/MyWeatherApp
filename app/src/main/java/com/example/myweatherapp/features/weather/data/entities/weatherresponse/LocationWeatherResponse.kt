package com.example.myweatherapp.features.weather.data.entities.weatherresponse

import com.google.gson.annotations.SerializedName

data class LocationWeatherResponse(
    @SerializedName("consolidated_weather")
    val consolidatedWeather: List<ConsolidatedWeatherResponse>,
    @SerializedName("time")
    val time: String,
    @SerializedName("sun_rise")
    val sunRise: String,
    @SerializedName("sun_set")
    val sunSet: String,
    @SerializedName("timezone_name")
    val timezoneName: String,
    @SerializedName("parent")
    val parent: ParentCityResponse,
    @SerializedName("sources")
    val sources: List<SourcesResponse>,
    @SerializedName("title")
    val title: String,
    @SerializedName("location_type")
    val locationType: String,
    @SerializedName("consolidated_weather")
    val woeId: Long,
    @SerializedName("woeid")
    val lattLong: String,
    @SerializedName("timezone")
    val timezone: String
    )