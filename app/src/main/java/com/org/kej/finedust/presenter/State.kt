package com.org.kej.finedust.presenter

import com.org.kej.finedust.domain.model.airquality.AirQualityModel
import com.org.kej.finedust.domain.model.monitoringstation.MonitoringStationModel
import com.org.kej.finedust.domain.model.weather.WeatherModel

sealed class State {
    object ERROR : State()

    data class SuccessMonitoringStation(
        val monitoringStation: MonitoringStationModel
    ) : State()

    data class SuccessMeasureVale(
        val airQualityModel: AirQualityModel
    ) : State()

    data class SuccessWeatherValue(
        val weatherList: List<WeatherModel>
    ) : State()

}
