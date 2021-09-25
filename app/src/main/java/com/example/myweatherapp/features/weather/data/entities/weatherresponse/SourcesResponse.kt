package com.example.myweatherapp.features.weather.data.entities.weatherresponse

import com.google.gson.annotations.SerializedName

data class SourcesResponse(
    @SerializedName("crawl_rate")
    val crawlRate: Int,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String
)