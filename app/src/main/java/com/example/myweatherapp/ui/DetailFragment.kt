package com.example.myweatherapp.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import com.example.myweatherapp.BuildConfig
import com.example.myweatherapp.base.BaseFragment
import com.example.myweatherapp.databinding.FragmentDetailBinding
import com.example.myweatherapp.features.weather.domain.mapper.toLocationWeatherInfo
import com.example.myweatherapp.util.Status
import com.example.myweatherapp.util.downloadImage
import com.example.myweatherapp.viewmodel.DetailViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment @Inject constructor(
    private val glide: RequestManager
) : BaseFragment<FragmentDetailBinding>() {

    private lateinit var viewModel: DetailViewModel
    private val args: DetailFragmentArgs by navArgs()

    override fun getViewBinding(): FragmentDetailBinding {
        return FragmentDetailBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(DetailViewModel::class.java)

        lifecycleScope.launch {
            viewModel.getLocationWeatherInfo(args.woeid)
        }

        observeStateFlow()

    }

    private fun observeStateFlow() {
        lifecycleScope.launch {
            viewModel.locationWeather.collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        binding.scrollView2.visibility = View.VISIBLE
                        binding.detailProgressBar.visibility = View.GONE
                        it.data?.let { response ->
                            val weatherResult = response.toLocationWeatherInfo()
/*
                            binding.ivMainTodayWeatherIcon.downloadImage(
                                glide,
                                BuildConfig.BASE_URL + "/static/img/weather/png/" + weatherResult.consolidatedWeather[0].weatherStateAbbr + ".png"
                            )

 */
                        }
                    }
                    Status.LOADING -> {
                        binding.detailProgressBar.visibility = View.VISIBLE
                        binding.scrollView2.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        binding.detailProgressBar.visibility = View.GONE
                        Snackbar.make(
                            binding.root,
                            it.message ?: "Error Occurred!",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

    }


}