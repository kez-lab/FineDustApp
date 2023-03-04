package com.org.kej.finedust.presenter

import com.org.kej.finedust.data.models.airquality.MeasuredValue
import com.org.kej.finedust.data.models.monitoringstation.MonitoringStation

sealed class DustState {
    object ERROR: DustState()

    data class SuccessMonitoringStation(
        val monitoringStation: MonitoringStation
    ) : DustState()

    data class SuccessMeasureVale(
        val MeasuredValue: MeasuredValue
    ) : DustState()

}
