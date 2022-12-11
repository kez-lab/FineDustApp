package com.org.kej.finedust.data.services

import com.org.kej.finedust.BuildConfig
import com.org.kej.finedust.data.models.tmcoodinates.TmCoordinatesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface KakaoLocationApiService {

    @Headers("Authorization:KakaoAK ${BuildConfig.KAKAO_API_KEY}")
    @GET("v2/local/geo/transcoord.json?output_coord=TM")
    suspend fun getTmCoodrinates(
        @Query("x") longitude:Double,
        @Query("y") latitude:Double
    ): Response<TmCoordinatesResponse>

}