package com.example.myweatherapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myweatherapp.databinding.RowLocationBinding
import com.example.myweatherapp.features.weather.domain.entities.location.NearLocations
import javax.inject.Inject

class LocationsRecyclerViewAdapter @Inject constructor() :
    RecyclerView.Adapter<LocationsRecyclerViewAdapter.LocationsViewHolder>() {

    private var locationClickListener: ((Int) -> Unit)? = null

    inner class LocationsViewHolder(private val itemViewBinding: RowLocationBinding) :
        RecyclerView.ViewHolder(itemViewBinding.root) {

        fun bind(data: NearLocations) {
            itemViewBinding.nearestCityName.text = data.title
        }

    }


    fun setOnLocationClickListener(listener: (Int) -> Unit) {
        locationClickListener = listener
    }


    private val differ = object : DiffUtil.ItemCallback<NearLocations>() {
        override fun areItemsTheSame(oldItem: NearLocations, newItem: NearLocations): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: NearLocations, newItem: NearLocations): Boolean {
            return oldItem == newItem
        }
    }
    private val asyncListDiffer = AsyncListDiffer(this, differ)

    var locations: List<NearLocations>
        get() = asyncListDiffer.currentList
        set(value) = asyncListDiffer.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsViewHolder {
        val rootView =
            RowLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationsViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: LocationsViewHolder, position: Int) {
        val locationInstance = locations[position]
        holder.bind(locationInstance)

        holder.itemView.setOnClickListener {
            locationClickListener?.let {
                it(locationInstance.woeId)
            }
        }
    }

    override fun getItemCount(): Int = locations.size
}