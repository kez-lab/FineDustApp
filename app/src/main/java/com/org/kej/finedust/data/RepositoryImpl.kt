package com.org.kej.finedust.data

import android.util.Log
import com.org.kej.finedust.data.models.airquality.MeasuredValue
import com.org.kej.finedust.data.models.monitoringstation.MonitoringStation
import com.org.kej.finedust.data.services.AirKoreaApiService
import com.org.kej.finedust.data.services.KakaoLocationApiService
import com.org.kej.finedust.domain.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val kakaoLocationApiService: KakaoLocationApiService,
    private val airKoreaApiService: AirKoreaApiService
) : Repository {
    override suspend fun getNearbyMonitoringStation(latitude: Double, longitude: Double): MonitoringStation? {
        val tmCoordinates = kakaoLocationApiService
            .getTmCoodrinates(longitude, latitude)
            .body()
            ?.documents
            ?.firstOrNull()

        val tmX = tmCoordinates?.x
        val tmY = tmCoordinates?.y
        Log.d("좌표계", "$tmX,$tmY")

        return airKoreaApiService.getNearbyMonitoringStation_air(tmX!!, tmY!!)
            .body()
            ?.response
            ?.body
            ?.monitoringStations
            ?.minByOrNull { it.tm ?: Double.MAX_VALUE }
    }
    override suspend fun getLatestAirQualityData(stationName: String): MeasuredValue? =
        airKoreaApiService.getRealtimeAirQualities(stationName)
            .body()
            ?.response
            ?.body
            ?.measuredValues
            ?.firstOrNull()
}