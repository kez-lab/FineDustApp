package com.org.kej.finedust.data.models.airquality


import com.google.gson.annotations.SerializedName
import com.org.kej.finedust.domain.model.airquality.AirQualityModel
import com.org.kej.finedust.domain.model.airquality.Grade

data class AirQualityResponse(
    @SerializedName("response")
    val response: Response?
) {
    data class Response(
        @SerializedName("body")
        val body: Body?,
        @SerializedName("header")
        val header: Header?
    ) {
        data class Body(
            @SerializedName("items")
            val measuredValues: List<MeasuredValue>?,
            @SerializedName("numOfRows")
            val numOfRows: Int?,
            @SerializedName("pageNo")
            val pageNo: Int?,
            @SerializedName("totalCount")
            val totalCount: Int?
        )

        data class Header(
            @SerializedName("resultCode")
            val resultCode: String?,
            @SerializedName("resultMsg")
            val resultMsg: String?
        )
    }
}

fun AirQualityResponse.toAirQuality() = AirQualityModel(
    numOfRows = response?.body?.numOfRows ?: 0,
    pageNo = response?.body?.pageNo ?: 0,
    totalCount = response?.body?.totalCount ?: 0,
    resultCode = response?.header?.resultCode ?: "",
    resultMsg = response?.header?.resultMsg ?: "",
    coFlag = response?.body?.measuredValues?.firstOrNull()?.coFlag ?: 0,
    coGrade = Grade.fromInt(response?.body?.measuredValues?.firstOrNull()?.coGrade),
    coValue = response?.body?.measuredValues?.firstOrNull()?.coValue ?: "",
    dataTime = response?.body?.measuredValues?.firstOrNull()?.dataTime ?: "",
    khaiGrade = Grade.fromInt(response?.body?.measuredValues?.firstOrNull()?.khaiGrade),
    khaiValue = response?.body?.measuredValues?.firstOrNull()?.khaiValue ?: "",
    mangName = response?.body?.measuredValues?.firstOrNull()?.mangName ?: "",
    no2Flag = response?.body?.measuredValues?.firstOrNull()?.no2Flag ?: 0,
    no2Grade = Grade.fromInt(response?.body?.measuredValues?.firstOrNull()?.no2Grade),
    no2Value = response?.body?.measuredValues?.firstOrNull()?.no2Value ?: "",
    o3Flag = response?.body?.measuredValues?.firstOrNull()?.o3Flag ?: 0,
    o3Grade = Grade.fromInt(response?.body?.measuredValues?.firstOrNull()?.o3Grade),
    o3Value = response?.body?.measuredValues?.firstOrNull()?.o3Value ?: "",
    pm10Flag = response?.body?.measuredValues?.firstOrNull()?.pm10Flag ?: 0,
    pm10Grade = Grade.fromInt(response?.body?.measuredValues?.firstOrNull()?.pm10Grade),
    pm10Grade1h = Grade.fromInt(response?.body?.measuredValues?.firstOrNull()?.pm10Grade1h),
    pm10Value = response?.body?.measuredValues?.firstOrNull()?.pm10Value ?: "",
    pm10Value24 = response?.body?.measuredValues?.firstOrNull()?.pm10Value24 ?: "",
    pm25Flag = response?.body?.measuredValues?.firstOrNull()?.pm25Flag ?: 0,
    pm25Grade = Grade.fromInt(response?.body?.measuredValues?.firstOrNull()?.pm25Grade),
    pm25Grade1h = Grade.fromInt(response?.body?.measuredValues?.firstOrNull()?.pm25Grade1h),
    pm25Value = response?.body?.measuredValues?.firstOrNull()?.pm25Value ?: "",
    pm25Value24 = response?.body?.measuredValues?.firstOrNull()?.pm25Value24 ?: "",
    so2Flag = response?.body?.measuredValues?.firstOrNull()?.so2Flag ?: 0,
    so2Grade = Grade.fromInt(response?.body?.measuredValues?.firstOrNull()?.so2Grade),
    so2Value = response?.body?.measuredValues?.firstOrNull()?.so2Value ?: "",
)