package com.org.kej.finedust.data.models.monitoringstation


import com.google.gson.annotations.SerializedName

data class MonitoringStationsResponse(
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
            val monitoringStations: List<MonitoringStation>?,
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