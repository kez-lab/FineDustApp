package com.org.kej.finedust.presenter

import com.org.kej.finedust.data.models.airquality.MeasuredValue
import com.org.kej.finedust.data.models.monitoringstation.MonitoringStation
import com.org.kej.finedust.domain.entity.AirQuality

sealed class DustState {
    object ERROR: DustState()

    data class SuccessMonitoringStation(
        val monitoringStation: MonitoringStation
    ) : DustState()

    data class SuccessMeasureVale(
        val airQuality: AirQuality
    ) : DustState()

}
