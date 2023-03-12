package com.org.kej.finedust.domain

import com.org.kej.finedust.data.models.weather.WeatherResponse
import com.org.kej.finedust.domain.model.airquality.AirQualityModel
import com.org.kej.finedust.domain.model.monitoringstation.MonitoringStationModel
import com.org.kej.finedust.domain.model.weather.WeatherModel
import retrofit2.Response
import retrofit2.http.Query

interface Repository {
    suspend fun getNearbyMonitoringStation(latitude: Double, longitude: Double): MonitoringStationModel?
    suspend fun getLatestAirQualityData(stationName: String): AirQualityModel?

    suspend fun getVillageForecast(baseDate: String, baseTime: String, nx: Int, ny: Int): List<WeatherModel>?

}