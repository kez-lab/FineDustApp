package com.org.kej.finedust.presenter

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.org.kej.finedust.data.Repository
import com.org.kej.finedust.data.models.monitoringstation.MonitoringStation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    private var _mainLiveData = MutableLiveData<MainState>()
    val mainLiveData: LiveData<MainState> = _mainLiveData

    fun getMonitoringStation(location: Location) {
        viewModelScope.launch {
            val monitoringStation = withContext(Dispatchers.IO) {
                Repository.getNearbyMonitoringStation(
                    location.latitude,
                    location.longitude
                )
            }
            _mainLiveData.postValue(
                monitoringStation?.let {
                    MainState.SuccessMonitoringStation(it)
                } ?: MainState.ERROR
            )
        }
    }

    fun getMeasuredValue(monitoringStation: MonitoringStation) {
        viewModelScope.launch {
            val measuredValue = withContext(Dispatchers.IO) {
                Repository.getLatestAirQualityData(monitoringStation.stationName.orEmpty())
            }
            _mainLiveData.postValue(
                measuredValue?.let {
                    MainState.SuccessMeasureVale(monitoringStation, it)
                } ?: MainState.ERROR
            )
        }

    }
}