package com.org.kej.finedust.data.models.weather

data class Item(
    val baseDate: String?,
    val baseTime: String?,
    val category: String?,
    val fcstDate: String?,
    val fcstTime: String?,
    val fcstValue: String?,
    val nx: Int?,
    val ny: Int?
)