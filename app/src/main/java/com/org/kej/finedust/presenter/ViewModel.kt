package com.org.kej.finedust.presenter

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.org.kej.finedust.domain.Repository
import com.org.kej.finedust.domain.model.weather.WeatherModel
import com.org.kej.finedust.domain.weather.Forecast
import com.org.kej.finedust.domain.weather.WeatherCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private var _stateLiveData = MutableLiveData<State>()
    val stateLiveData: LiveData<State> = _stateLiveData

    fun getMonitoringStation(location: Location) {
        viewModelScope.launch {
            val monitoringStation = withContext(Dispatchers.IO) {
                repository.getNearbyMonitoringStation(
                    location.latitude, location.longitude
                )
            }
            _stateLiveData.postValue(monitoringStation?.let {
                State.SuccessMonitoringStation(it)
            } ?: State.ERROR)
        }
    }

    fun getMeasuredValue(stationName: String) {
        if (stationName.isEmpty()) {
            _stateLiveData.postValue(State.ERROR)
            return
        }
        viewModelScope.launch {
            val measuredValue = withContext(Dispatchers.IO) {
                repository.getLatestAirQualityData(stationName)
            }
            _stateLiveData.postValue(measuredValue?.let {
                State.SuccessMeasureVale(it)
            } ?: State.ERROR)
        }
    }

    //SMAPLE DATA
    fun getVillageForecast(baseData: String, baseTime: String) {
        viewModelScope.launch {
            val weatherList = withContext(Dispatchers.IO) {
                repository.getVillageForecast(baseData, baseTime, 55, 127)
            }
            val weatherMap = mutableMapOf<String, Forecast>()
            weatherList?.forEach { forecast ->
                weatherMap[getWeatherKey(forecast)] = Forecast(forecast.baseDate, forecast.baseTime)
                weatherMap[getWeatherKey(forecast)]?.apply {
                    when (forecast.category) {
                        WeatherCategory.POP -> {
                            precipitation = forecast.fcstValue.toInt()
                        }
                        WeatherCategory.PTY -> {
                            precipitationType = transFormPrecipitationType(forecast)
                        }
                        WeatherCategory.SKY -> {
                            sky = transFormSky(forecast)
                        }
                        WeatherCategory.TMP -> {
                            temperature = forecast.fcstValue.toDouble()
                        }
                        else -> {}
                    }
                }
            }

        }
    }

    private fun transFormSky(forecast: WeatherModel): String {
        return when (forecast.fcstValue.toInt()) {
            1 -> "맑음"
            3 -> "구름 많음"
            4 -> "흐림"
            else -> ""
        }
    }

    private fun transFormPrecipitationType(forecast: WeatherModel) = when (forecast.fcstValue.toInt()) {
        1 -> "비"
        2 -> "비/눈"
        3 -> "눈"
        4 -> "소나기"
        else -> "없음"
    }

    private fun getWeatherKey(weather: WeatherModel) = "${weather.baseDate}/${weather.baseTime}"
}