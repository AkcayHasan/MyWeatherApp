package com.example.myweatherapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myweatherapp.App
import com.example.myweatherapp.adapter.LocationsRecyclerViewAdapter
import com.example.myweatherapp.base.BaseFragment
import com.example.myweatherapp.databinding.FragmentHomeBinding
import com.example.myweatherapp.features.weather.domain.mapper.toNearLocations
import com.example.myweatherapp.util.LocationTrackListener
import com.example.myweatherapp.util.Status
import com.example.myweatherapp.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment @Inject constructor(
    private val locationsRecyclerViewAdapter: LocationsRecyclerViewAdapter
) : BaseFragment<FragmentHomeBinding>(), LocationTrackListener {

    private lateinit var viewModel: HomeViewModel
    private var job: Job? = null

    override fun getViewBinding(): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).setOnlocationTrackListener(this)

        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

        binding.rvLocations.apply {
            adapter = locationsRecyclerViewAdapter
            layoutManager = LinearLayoutManager(binding.root.context)
        }

        subscribeToViewModel()

        binding.searchText.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch {
                delay(700)
                it?.let {
                    if (it.toString().isNotEmpty()) {
                        viewModel.getNearLocations(it.toString(), null)
                    } else {
                        viewModel.getNearLocations(
                            null,
                            "${App.currentLatitude},${App.currentLongitude}"
                        )
                    }
                }
            }
        }

        locationsRecyclerViewAdapter.setOnLocationClickListener {
            val action = HomeFragmentDirections.homeFragmentToDetailFragment(it)
            findNavController().navigate(action)
        }


    }

    private fun subscribeToViewModel() {
        lifecycleScope.launch {
            viewModel.listNearLocations.collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        binding.progressBar.visibility = View.GONE
                        it.data?.let { listNearLocationsResponse ->
                            val listNearLocations =
                                listNearLocationsResponse.map { nearLocationsResponse ->
                                    nearLocationsResponse.toNearLocations()
                                }
                            locationsRecyclerViewAdapter.locations = listNearLocations
                        }
                    }
                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    Status.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(binding.root.context, "Error Occurred!", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
    }

    override fun locationTrack(isAllowed: Boolean) {
        if (isAllowed) {
            job = lifecycleScope.launch {
                viewModel.getNearLocations(
                    null,
                    "${App.currentLatitude},${App.currentLongitude}"
                )
            }
        }
    }

}