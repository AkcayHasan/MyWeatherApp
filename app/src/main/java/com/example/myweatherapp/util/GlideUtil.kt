package com.example.myweatherapp.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myweatherapp.BuildConfig
import com.example.myweatherapp.R

fun ImageView.downloadImage(url: String, context: Context) {
    val options =
        RequestOptions().placeholder(placeHolder(context)).error(R.mipmap.ic_launcher_round)
    Glide.with(this.context).setDefaultRequestOptions(options)
        .load(BuildConfig.IMAGE_URL + "static/img/weather/png/" + url + ".png").into(this)
}

fun placeHolder(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 4f
        centerRadius = 20f
        start()
    }
}