package com.example.myweatherapp.ui

import android.os.Bundle
import android.view.View
import com.example.myweatherapp.base.BaseFragment
import com.example.myweatherapp.databinding.FragmentDetailBinding

class DetailFragment : BaseFragment<FragmentDetailBinding>() {
    override fun getViewBinding(): FragmentDetailBinding {
        return FragmentDetailBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}