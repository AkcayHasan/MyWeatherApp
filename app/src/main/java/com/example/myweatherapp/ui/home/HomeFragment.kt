package com.example.myweatherapp.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myweatherapp.R
import com.example.myweatherapp.adapter.LocationsRecyclerViewAdapter
import com.example.myweatherapp.base.BaseFragment
import com.example.myweatherapp.databinding.FragmentHomeBinding
import com.example.myweatherapp.ui.MainActivity
import com.example.myweatherapp.util.LocationTrackListener
import com.example.myweatherapp.util.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment constructor(
    private val locationsRecyclerViewAdapter: LocationsRecyclerViewAdapter
) : BaseFragment<FragmentHomeBinding>(), LocationTrackListener {

    private lateinit var viewModel: HomeViewModel
    private var job: Job? = null

    override fun getViewBinding(): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).setOnLocationTrackListener(this)
        val toolbarListener = activity as MainActivity

        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

        toolbarListener.toolbarName(binding.root.context.getString(R.string.app_name))
        toolbarListener.toolbarBackButton(false)

        binding.rvLocations.apply {
            adapter = locationsRecyclerViewAdapter
            layoutManager = LinearLayoutManager(binding.root.context)
        }

        observeStateFlow()

        locationsRecyclerViewAdapter.setOnLocationClickListener {
            toolbarListener.toolbarName(it.title)
            toolbarListener.toolbarBackButton(true)

            val action = HomeFragmentDirections.homeFragmentToDetailFragment(it.woeId)
            findNavController().navigate(action)
        }

    }

    override fun onResume() {
        super.onResume()
        binding.searchText.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch {
                delay(700)
                it?.let {
                    if (it.toString().isNotEmpty()) {
                        viewModel.getNearLocations(it.toString())
                    } else {
                        viewModel.getNearLocations(
                            null
                        )
                    }
                }
            }
        }
    }

    private fun observeStateFlow() {
        lifecycleScope.launch {
            viewModel.listNearLocations.collect {
                  when (it.status) {
                       Status.SUCCESS -> {
                           binding.homeProgressBar.visibility = View.GONE
                           binding.rvLocations.visibility = View.VISIBLE
                           it.data?.let { listNearLocationsResponse ->
                               locationsRecyclerViewAdapter.locations = listNearLocationsResponse
                           }
                       }
                       Status.LOADING -> {
                           binding.rvLocations.visibility = View.GONE
                           binding.homeProgressBar.visibility = View.VISIBLE
                       }
                       Status.ERROR -> {
                           binding.homeProgressBar.visibility = View.GONE
                           Toast.makeText(binding.root.context, it.message?: "Error Occurred!", Toast.LENGTH_LONG)
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
                    null
                )
            }
        }
    }

}