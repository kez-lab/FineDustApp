package com.org.kej.finedust.presenter.splash

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.tasks.CancellationTokenSource
import com.org.kej.finedust.util.DustUtil
import com.org.kej.finedust.R
import com.org.kej.finedust.presenter.State
import com.org.kej.finedust.presenter.ViewModel
import com.org.kej.finedust.presenter.main.MainActivity
import com.org.kej.finedust.util.DialogUtil
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    companion object {
        const val STATION_NAME = "STATION_NAME"
        const val ADDR = "ADDR"
        const val LON = "LON"
        const val LAT = "LAT"
    }

    private lateinit var currentLocation: Location
    private var cancellationTokenSource: CancellationTokenSource? = null

    private val viewModel by viewModels<ViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        requestLocationPermission()
        initObserve()
    }

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
        viewModel.stateLiveData.observe(this) {
            when (it) {
                is State.SuccessMonitoringStation -> {
                    Intent(this, MainActivity::class.java).run {
                        putExtra(STATION_NAME, it.monitoringStation.stationName)
                        putExtra(ADDR, it.monitoringStation.addr)
                        putExtra(LAT, currentLocation.latitude)
                        putExtra(LON, currentLocation.longitude)
                    }.let(::startActivity)
                    finish()
                }
                else -> {
                    DialogUtil.showErrorDialog(this)
                }
            }
        }
    }

    private fun getStationData() {
        cancellationTokenSource = CancellationTokenSource()
        cancellationTokenSource?.let {
            DustUtil.fetchAirQualityData(this, it) {location ->
                currentLocation = location
                viewModel.getMonitoringStation(location)
            }
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