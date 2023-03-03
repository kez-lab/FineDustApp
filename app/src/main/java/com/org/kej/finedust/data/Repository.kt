package com.org.kej.finedust.data

import android.util.Log
import com.org.kej.finedust.BuildConfig
import com.org.kej.finedust.data.models.airquality.MeasuredValue
import com.org.kej.finedust.data.models.monitoringstation.MonitoringStation
import com.org.kej.finedust.data.services.AirKoreaApiService
import com.org.kej.finedust.data.services.KakaoLocationApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object Repository {
    suspend fun getNearbyMonitoringStation(latitude:Double, longitude:Double): MonitoringStation? {
        val tmCoordinates = kakaoLocationApiService
            .getTmCoodrinates(longitude, latitude)
            .body()
            ?.documents
            ?.firstOrNull()

        val tmX = tmCoordinates?.x
        val tmY = tmCoordinates?.y
        Log.d("좌표계","$tmX,$tmY")

        return airKoreaApiService.getNearbyMonitoringStation_air(tmX!!,tmY!!)
            .body()
            ?.response
            ?.body
            ?.monitoringStations
            ?.minByOrNull { it.tm ?: Double.MAX_VALUE }
    }

    suspend fun getLatestAirQualityData(stationName:String):  MeasuredValue? =
        airKoreaApiService.getRealtimeAirQualities(stationName)
            .body()
            ?.response
            ?.body
            ?.measuredValues
            ?.firstOrNull()



    private val kakaoLocationApiService: KakaoLocationApiService by lazy {
        Retrofit.Builder()
            .baseUrl(Url.KAKAO_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(buildHttpClient())
            .build()
            .create()
    }


    private val airKoreaApiService: AirKoreaApiService by lazy {
        Retrofit.Builder()
            .baseUrl(Url.AIR_KOREA_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(buildHttpClient())
            .build()
            .create()
    }


    private fun buildHttpClient():OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG){
                        HttpLoggingInterceptor.Level.BODY
                    } else{
                        HttpLoggingInterceptor.Level.NONE
                    }
                }
            )
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .build()
}