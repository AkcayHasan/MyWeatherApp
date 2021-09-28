package com.example.myweatherapp.util

interface ToolbarChangesListener {
    fun toolbarName(title: String)
    fun toolbarBackButton(isDetailFragment: Boolean)
}