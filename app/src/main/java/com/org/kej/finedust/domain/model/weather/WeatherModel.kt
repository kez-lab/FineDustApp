package com.org.kej.finedust.domain.model.weather

import com.org.kej.finedust.domain.weather.WeatherCategory

data class WeatherModel(
    val baseDate: String,
    val baseTime: String,
    val category: WeatherCategory?,
    val fcstDate: String,
    val fcstTime: String,
    val fcstValue: String,
    val nx: Int,
    val ny: Int
)