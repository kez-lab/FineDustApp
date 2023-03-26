package com.org.kej.finedust.data.models.weather

import com.org.kej.finedust.domain.weather.WeatherCategory

data class Item(
    val baseDate: String?,
    val baseTime: String?,
    val category: WeatherCategory?,
    val fcstDate: String?,
    val fcstTime: String?,
    val fcstValue: String?,
    val nx: Int?,
    val ny: Int?
)