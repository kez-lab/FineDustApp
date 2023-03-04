package com.org.kej.finedust.data.models.airquality


import com.google.gson.annotations.SerializedName
import com.org.kej.finedust.domain.entity.AirQuality

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

fun AirQualityResponse.toAirQuality() = AirQuality(
    numOfRows = this.response?.body?.numOfRows ?:0,
    pageNo =this.response?.body?.pageNo ?:0,
    totalCount =this.response?.body?.totalCount ?:0,
    resultCode =this.response?.header?.resultCode ?:"",
    resultMsg =this.response?.header?.resultMsg ?:"",
    coFlag =this.response?.body?.measuredValues?.firstOrNull()?.coFlag ?:0,
    coGrade =this.response?.body?.measuredValues?.firstOrNull()?.coGrade ?:Grade.UNKNOWN,
    coValue =this.response?.body?.measuredValues?.firstOrNull()?.coValue ?:"",
    dataTime =this.response?.body?.measuredValues?.firstOrNull()?.dataTime ?:"",
    khaiGrade =this.response?.body?.measuredValues?.firstOrNull()?.khaiGrade ?:Grade.UNKNOWN,
    khaiValue =this.response?.body?.measuredValues?.firstOrNull()?.khaiValue ?:"",
    mangName =this.response?.body?.measuredValues?.firstOrNull()?.mangName ?:"",
    no2Flag =this.response?.body?.measuredValues?.firstOrNull()?.no2Flag ?:0,
    no2Grade =this.response?.body?.measuredValues?.firstOrNull()?.no2Grade ?:Grade.UNKNOWN,
    no2Value =this.response?.body?.measuredValues?.firstOrNull()?.no2Value ?:"",
    o3Flag =this.response?.body?.measuredValues?.firstOrNull()?.o3Flag ?:0,
    o3Grade =this.response?.body?.measuredValues?.firstOrNull()?.o3Grade ?:Grade.UNKNOWN,
    o3Value =this.response?.body?.measuredValues?.firstOrNull()?.o3Value ?:"",
    pm10Flag =this.response?.body?.measuredValues?.firstOrNull()?.pm10Flag ?:0,
    pm10Grade =this.response?.body?.measuredValues?.firstOrNull()?.pm10Grade ?:Grade.UNKNOWN,
    pm10Grade1h =this.response?.body?.measuredValues?.firstOrNull()?.pm10Grade1h ?:Grade.UNKNOWN,
    pm10Value =this.response?.body?.measuredValues?.firstOrNull()?.pm10Value ?:"",
    pm10Value24 =this.response?.body?.measuredValues?.firstOrNull()?.pm10Value24 ?:"",
    pm25Flag =this.response?.body?.measuredValues?.firstOrNull()?.pm25Flag ?:0,
    pm25Grade =this.response?.body?.measuredValues?.firstOrNull()?.pm25Grade ?:Grade.UNKNOWN,
    pm25Grade1h =this.response?.body?.measuredValues?.firstOrNull()?.pm25Grade1h ?:Grade.UNKNOWN,
    pm25Value =this.response?.body?.measuredValues?.firstOrNull()?.pm25Value ?:"",
    pm25Value24 =this.response?.body?.measuredValues?.firstOrNull()?.pm25Value24 ?:"",
    so2Flag =this.response?.body?.measuredValues?.firstOrNull()?.so2Flag ?:0,
    so2Grade =this.response?.body?.measuredValues?.firstOrNull()?.so2Grade ?:Grade.UNKNOWN,
    so2Value =this.response?.body?.measuredValues?.firstOrNull()?.so2Value ?:"",
)