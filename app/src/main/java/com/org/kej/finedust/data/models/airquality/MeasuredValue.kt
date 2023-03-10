package com.org.kej.finedust.data.models.airquality


import com.google.gson.annotations.SerializedName
import com.org.kej.finedust.domain.model.Grade

data class MeasuredValue(
    @SerializedName("coFlag")
    val coFlag: Any?,
    @SerializedName("coGrade")
    val coGrade: Int?,
    @SerializedName("coValue")
    val coValue: String?,
    @SerializedName("dataTime")
    val dataTime: String?,
    @SerializedName("khaiGrade")
    val khaiGrade: Int?,
    @SerializedName("khaiValue")
    val khaiValue: String?,
    @SerializedName("mangName")
    val mangName: String?,
    @SerializedName("no2Flag")
    val no2Flag: Any?,
    @SerializedName("no2Grade")
    val no2Grade: Int?,
    @SerializedName("no2Value")
    val no2Value: String?,
    @SerializedName("o3Flag")
    val o3Flag: Any?,
    @SerializedName("o3Grade")
    val o3Grade: Int?,
    @SerializedName("o3Value")
    val o3Value: String?,
    @SerializedName("pm10Flag")
    val pm10Flag: Any?,
    @SerializedName("pm10Grade")
    val pm10Grade: Int?,
    @SerializedName("pm10Grade1h")
    val pm10Grade1h: Int?,
    @SerializedName("pm10Value")
    val pm10Value: String?,
    @SerializedName("pm10Value24")
    val pm10Value24: String?,
    @SerializedName("pm25Flag")
    val pm25Flag: Any?,
    @SerializedName("pm25Grade")
    val pm25Grade: Int?,
    @SerializedName("pm25Grade1h")
    val pm25Grade1h: Int?,
    @SerializedName("pm25Value")
    val pm25Value: String?,
    @SerializedName("pm25Value24")
    val pm25Value24: String?,
    @SerializedName("so2Flag")
    val so2Flag: Any?,
    @SerializedName("so2Grade")
    val so2Grade: Int?,
    @SerializedName("so2Value")
    val so2Value: String?
)