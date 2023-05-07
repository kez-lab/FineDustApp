package com.org.kej.finedust.presenter.main.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.org.kej.finedust.databinding.ItemWeatherBinding
import com.org.kej.finedust.domain.weather.Forecast


class WeatherAdapter : ListAdapter<Forecast, WeatherAdapter.WeatherViewHolder>(diffUtil) {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Forecast>() {
            override fun areItemsTheSame(oldItem: Forecast, newItem: Forecast): Boolean {
                return oldItem.forecastTime == newItem.forecastTime
            }

            override fun areContentsTheSame(oldItem: Forecast, newItem: Forecast): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder = WeatherViewHolder(ItemWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class WeatherViewHolder(private val binding: ItemWeatherBinding) : ViewHolder(binding.root) {
        fun bind(item: Forecast) {
            binding.run {
                tvTime.text = item.forecastTime
                tvTemp.text = item.temperature.toString()
                tvWeather.text = item.precipitationType
            }
        }
    }
}