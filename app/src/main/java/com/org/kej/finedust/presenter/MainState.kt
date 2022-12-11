package com.org.kej.finedust.presenter

import com.org.kej.finedust.data.models.airquality.MeasuredValue
import com.org.kej.finedust.data.models.monitoringstation.MonitoringStation

sealed class MainState {

    object ERROR: MainState()

    data class SuccessMonitoringStation(
        val monitoringStation: MonitoringStation
    ) : MainState()

    data class SuccessMeasureVale(
        val monitoringStation: MonitoringStation,
        val MeasuredValue: MeasuredValue
    ) : MainState()

}
