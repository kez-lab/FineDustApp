package com.org.kej.finedust.domain

import com.org.kej.finedust.data.models.airquality.MeasuredValue
import com.org.kej.finedust.data.models.monitoringstation.MonitoringStation
import com.org.kej.finedust.domain.entity.AirQuality

interface Repository {
    suspend fun getNearbyMonitoringStation(latitude:Double, longitude:Double): MonitoringStation?
    suspend fun getLatestAirQualityData(stationName:String): AirQuality?

}