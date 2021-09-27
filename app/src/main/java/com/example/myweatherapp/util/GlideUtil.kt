package com.example.myweatherapp.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.myweatherapp.BuildConfig

fun ImageView.downloadImage(url: String){
    Glide.with(this.context).load(BuildConfig.IMAGE_URL +"static/img/weather/png/" +url+".png").into(this);
}