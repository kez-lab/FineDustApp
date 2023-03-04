package com.org.kej.finedust.presenter

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.org.kej.finedust.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DustViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private var _dustLiveData = MutableLiveData<DustState>()
    val dustLiveData: LiveData<DustState> = _dustLiveData

    fun getMonitoringStation(location: Location) {
        viewModelScope.launch {
            val monitoringStation = withContext(Dispatchers.IO) {
                repository.getNearbyMonitoringStation(
                    location.latitude, location.longitude
                )
            }
            _dustLiveData.postValue(monitoringStation?.let {
                DustState.SuccessMonitoringStation(it)
            } ?: DustState.ERROR)
        }
    }

    fun getMeasuredValue(stationName: String) {
        if (stationName.isEmpty()) {
            _dustLiveData.postValue(DustState.ERROR)
            return
        }
        viewModelScope.launch {
            val measuredValue = withContext(Dispatchers.IO) {
                repository.getLatestAirQualityData(stationName)
            }
            _dustLiveData.postValue(measuredValue?.let {
                DustState.SuccessMeasureVale(it)
            } ?: DustState.ERROR)
        }

    }
}