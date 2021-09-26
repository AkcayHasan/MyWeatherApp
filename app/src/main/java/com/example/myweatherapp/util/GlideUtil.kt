package com.example.myweatherapp.util

import android.widget.ImageView
import com.bumptech.glide.RequestManager

fun ImageView.downloadImage(glide: RequestManager, url: String){
    glide.load(url).into(this)
}