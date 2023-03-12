package com.org.kej.finedust.data

import android.util.Log
import com.org.kej.finedust.data.models.airquality.toAirQuality
import com.org.kej.finedust.data.models.monitoringstation.toMonitoringStationModel
import com.org.kej.finedust.data.models.weather.toWeatherModelList
import com.org.kej.finedust.data.services.AirKoreaApiService
import com.org.kej.finedust.data.services.KakaoLocationApiService
import com.org.kej.finedust.domain.Repository
import com.org.kej.finedust.domain.model.airquality.AirQualityModel
import com.org.kej.finedust.domain.model.monitoringstation.MonitoringStationModel
import com.org.kej.finedust.domain.model.weather.WeatherModel
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val kakaoLocationApiService: KakaoLocationApiService,
    private val airKoreaApiService: AirKoreaApiService
) : Repository {
    override suspend fun getNearbyMonitoringStation(latitude: Double, longitude: Double): MonitoringStationModel? {
        val tmCoordinates = kakaoLocationApiService
            .getTmCoodrinates(longitude, latitude)
            .body()
            ?.documents
            ?.firstOrNull()

        val tmX = tmCoordinates?.x
        val tmY = tmCoordinates?.y
        Log.d("좌표계", "$tmX,$tmY")

        return airKoreaApiService.getNearbyMonitoringStationAir(tmX!!, tmY!!)
            .body()
            ?.response
            ?.body
            ?.monitoringStations
            ?.minByOrNull { it.tm ?: Double.MAX_VALUE }
            ?.toMonitoringStationModel()
    }
    override suspend fun getLatestAirQualityData(stationName: String): AirQualityModel? =
        airKoreaApiService.getRealtimeAirQualities(stationName)
                .body()?.toAirQuality()

    override suspend fun getVillageForecast(baseDate: String, baseTime: String, nx: Int, ny: Int): List<WeatherModel>? =
        airKoreaApiService.getVillageForecast(baseDate, baseTime, nx, ny).body()?.toWeatherModelList()


}
