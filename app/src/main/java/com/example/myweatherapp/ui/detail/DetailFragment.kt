package com.example.myweatherapp.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import com.example.myweatherapp.BuildConfig
import com.example.myweatherapp.base.BaseFragment
import com.example.myweatherapp.databinding.FragmentDetailBinding
import com.example.myweatherapp.util.Status
import com.example.myweatherapp.util.downloadImage
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>() {

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

    @SuppressLint("SetTextI18n")
    private fun observeStateFlow() {
        lifecycleScope.launch {
            viewModel.locationWeather.collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        binding.scrollView2.visibility = View.VISIBLE
                        binding.detailProgressBar.visibility = View.GONE
                        it.data?.let { response ->
                            binding.apply {
                                ivMainTodayWeatherIcon.downloadImage(
                                    response.consolidatedWeather[0].weatherStateAbbr,
                                    binding.root.context
                                )
                                tvMainTodayWeatherSunRise.text = response.sunRise
                                tvMainTodayWeatherSunSet.text = response.sunSet
                                tvMainTodayWeatherTime.text = response.time
                                tvMainTodayWeatherWind.text = "${response.consolidatedWeather[0].windSpeed.toInt()} Mph"
                                tvMainTodayWeather.text = "${response.consolidatedWeather[0].theTemp.toInt()}°C"
                                tvMainTodayWeatherState.text = response.consolidatedWeather[0].weatherStateName
                                tvMainTodayWeatherHumidity.text = "%${response.consolidatedWeather[0].humidity}"
                                tvMainTodayWeatherVisibility.text = "${response.consolidatedWeather[0].visibility.toInt()} miles"
                                tvMainTodayWeatherTimeZone.text = response.timeZone
                                ivTodayWeatherIcon.downloadImage(
                                    response.consolidatedWeather[0].weatherStateAbbr,
                                    binding.root.context
                                )
                                tvTodayWeatherMinTemp.text = "${response.consolidatedWeather[0].minTemp.toInt()}°C"
                                tvTodayWeatherMaxTemp.text = "${response.consolidatedWeather[0].maxTemp.toInt()}°C"

                                ivTomorrowWeatherIcon.downloadImage(
                                    response.consolidatedWeather[1].weatherStateAbbr,
                                    binding.root.context
                                )
                                tvTomorrowWeatherMinTemp.text = "${response.consolidatedWeather[1].minTemp.toInt()}°C"
                                tvTomorrowWeatherMaxTemp.text = "${response.consolidatedWeather[1].maxTemp.toInt()}°C"

                                ivDays1WeatherIcon.downloadImage(
                                    response.consolidatedWeather[2].weatherStateAbbr,
                                    binding.root.context
                                )
                                tvDays1WeatherMinTemp.text = "${response.consolidatedWeather[2].minTemp.toInt()}°C"
                                tvDays1WeatherMaxTemp.text = "${response.consolidatedWeather[2].maxTemp.toInt()}°C"
                                tvDays1Name.text = response.consolidatedWeather[2].applicableDate

                                ivDays2WeatherIcon.downloadImage(
                                    response.consolidatedWeather[3].weatherStateAbbr,
                                    binding.root.context
                                )
                                tvDays2WeatherMinTemp.text = "${response.consolidatedWeather[3].minTemp.toInt()}°C"
                                tvDays2WeatherMaxTemp.text = "${response.consolidatedWeather[3].maxTemp.toInt()}°C"
                                tvDays2Name.text = response.consolidatedWeather[3].applicableDate

                                ivDays3WeatherIcon.downloadImage(
                                    response.consolidatedWeather[4].weatherStateAbbr,
                                    binding.root.context
                                )
                                tvDays3WeatherMinTemp.text = "${response.consolidatedWeather[4].minTemp.toInt()}°C"
                                tvDays3WeatherMaxTemp.text = "${response.consolidatedWeather[4].maxTemp.toInt()}°C"
                                tvDays3Name.text = response.consolidatedWeather[4].applicableDate

                                ivDays4WeatherIcon.downloadImage(
                                    response.consolidatedWeather[5].weatherStateAbbr,
                                    binding.root.context
                                )
                                tvDays4WeatherMinTemp.text = "${response.consolidatedWeather[5].minTemp.toInt()}°C"
                                tvDays4WeatherMaxTemp.text = "${response.consolidatedWeather[5].maxTemp.toInt()}°C"
                                tvDays4Name.text = response.consolidatedWeather[5].applicableDate
                            }

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