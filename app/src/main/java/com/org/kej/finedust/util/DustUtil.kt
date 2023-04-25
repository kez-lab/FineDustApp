package com.org.kej.finedust.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.org.kej.finedust.presenter.ViewModel

object DustUtil {
    @SuppressLint("MissingPermission")
    fun fetchAirQualityData(context: Context, cancellationTokenSource: CancellationTokenSource, onLocationSuccessListener: (Location) -> Unit) {
        val activity = context as? Activity ?: kotlin.run {
            DialogUtil.showErrorDialog(context)
            return
        }

        LocationServices.getFusedLocationProviderClient(activity)
            .getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cancellationTokenSource.token)
            .addOnSuccessListener { location ->
                try {
                    onLocationSuccessListener(location)
                } catch (e: Exception) {
                    DialogUtil.showErrorDialog(context)
                }
            }
    }
}