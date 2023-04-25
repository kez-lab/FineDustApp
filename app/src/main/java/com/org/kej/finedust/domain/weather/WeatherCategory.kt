package com.org.kej.finedust.domain.weather

import com.google.gson.annotations.SerializedName
import com.org.kej.finedust.R

enum class WeatherCategory {
    @SerializedName("POP")
    POP, // 강수 확률
    @SerializedName("PTY")
    PTY, // 강수 형태
    @SerializedName("SKY")
    SKY, // 하늘 상태
    @SerializedName("TMP")
    TMP, // 1시간 기온
    UNKNOWN;
}