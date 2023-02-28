package com.org.kej.finedust.presenter.splash

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import com.org.kej.finedust.DustUtil
import com.org.kej.finedust.R
import com.org.kej.finedust.presenter.DustState
import com.org.kej.finedust.presenter.DustViewModel
import com.org.kej.finedust.presenter.main.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    companion object {
        const val STATION_NAME = "stationName"
        const val ADDR = "addr"
    }

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private var cancellationTokenSource: CancellationTokenSource? = null

    private val viewModel by viewModels<DustViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        requestLocationPermission()
        initObserve()
    }

    override fun onDestroy() {
        super.onDestroy()
        cancellationTokenSource?.cancel()
    }

    @SuppressLint("MissingPermission", "SetTextI18n")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val locationPermissionGranted =
            requestCode == MainActivity.REQUEST_ACCESS_LOCATION_PERMISSIONS &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED

        if (!locationPermissionGranted) {
            finish()
        } else {
            getStationData()
        }
    }

    private fun initObserve() {
        viewModel.dustLiveData.observe(this) {
            when (it) {
                is DustState.SuccessMonitoringStation -> {
                    Intent(this, MainActivity::class.java).run {
                        putExtra(STATION_NAME, it.monitoringStation.stationName)
                        putExtra(ADDR, it.monitoringStation.addr)
                    }.let(::startActivity)
                    finish()
                }
                else -> {
                    //Todo ERROR 팝업
                }
            }
        }
    }

    private fun getStationData() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        cancellationTokenSource = CancellationTokenSource()
        cancellationTokenSource?.let {
            DustUtil.fetchAirQualityData(this, viewModel, it, fusedLocationProviderClient)
        }
    }


    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            MainActivity.REQUEST_ACCESS_LOCATION_PERMISSIONS
        )
    }
}