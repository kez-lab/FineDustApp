package com.org.kej.finedust.presenter

import com.org.kej.finedust.domain.model.AirQualityModel
import com.org.kej.finedust.domain.model.MonitoringStationModel

sealed class DustState {
    object ERROR: DustState()

    data class SuccessMonitoringStation(
        val monitoringStation: MonitoringStationModel
    ) : DustState()

    data class SuccessMeasureVale(
        val airQualityModel: AirQualityModel
    ) : DustState()

}
