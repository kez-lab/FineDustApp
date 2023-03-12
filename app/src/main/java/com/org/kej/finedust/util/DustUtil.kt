package com.org.kej.finedust.util

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.tasks.CancellationTokenSource
import com.org.kej.finedust.presenter.ViewModel

object DustUtil {
    @SuppressLint("MissingPermission")
    fun fetchAirQualityData(context: Context, dustViewModel:ViewModel, cancellationTokenSource: CancellationTokenSource, fusedLocationProviderClient: FusedLocationProviderClient) {
        fusedLocationProviderClient.getCurrentLocation(
            LocationRequest.PRIORITY_HIGH_ACCURACY,
            cancellationTokenSource.token
        ).addOnSuccessListener { location ->
            try {
                dustViewModel.getMonitoringStation(location)
            } catch (e: Exception) {
                DialogUtil.showErrorDialog(context)
            }
        }
    }
}