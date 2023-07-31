package com.org.kej.finedust.domain

import com.org.kej.finedust.domain.model.AirQualityModel
import com.org.kej.finedust.domain.model.MonitoringStationModel

interface Repository {
    suspend fun getNearbyMonitoringStation(latitude:Double, longitude:Double): MonitoringStationModel?
    suspend fun getLatestAirQualityData(stationName:String): AirQualityModel?

}