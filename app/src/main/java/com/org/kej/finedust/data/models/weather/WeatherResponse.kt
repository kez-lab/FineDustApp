package com.org.kej.finedust.data.models.weather

import com.org.kej.finedust.domain.model.weather.WeatherModel

data class WeatherResponse(
    val response: Response?
) {
    data class Response(
        val body: Body?,
        val header: Header?
    ) {
        data class Body(
            val dataType: String?,
            val items: Items?,
            val numOfRows: Int?,
            val pageNo: Int?,
            val totalCount: Int?
        ) {
            data class Items(
                val item: List<Item>?
            )
        }

        data class Header(
            val resultCode: String?,
            val resultMsg: String?
        )
    }
}

fun WeatherResponse.toWeatherModelList(): List<WeatherModel> {
    val list = arrayListOf<WeatherModel>()
    response?.body?.items?.item?.forEach {
        list.add(
            WeatherModel(
                baseDate = it.baseDate ?: "",
                baseTime = it.baseTime ?: "",
                category = it.category ?: "",
                fcstDate = it.fcstDate ?: "",
                fcstTime = it.fcstTime ?: "",
                fcstValue = it.fcstValue ?: "",
                nx = it.nx ?: 0,
                ny = it.ny ?: 0,
            )
        )
    }
    return list
}
