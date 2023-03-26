package com.org.kej.finedust.domain.weather

data class Forecast(
    val forecastDate: String,
    val forecastTime: String,
    var temperature: Double = 0.0,
    var sky: String = "",
    var precipitation: Int = 0,
    var precipitationType: String = ""
)