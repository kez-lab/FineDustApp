package com.org.kej.finedust.presenter.main

import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import com.org.kej.finedust.databinding.ActivityMainBinding
import com.org.kej.finedust.domain.model.airquality.AirQualityModel
import com.org.kej.finedust.presenter.State
import com.org.kej.finedust.presenter.ViewModel
import com.org.kej.finedust.presenter.splash.SplashActivity
import com.org.kej.finedust.util.DialogUtil
import com.org.kej.finedust.util.DustUtil
import dagger.hilt.android.AndroidEntryPoint
import org.joda.time.LocalDateTime
import org.joda.time.LocalTime

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_ACCESS_LOCATION_PERMISSIONS = 100
    }

    private lateinit var stationName: String
    private lateinit var addr: String

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var cancellationTokenSource: CancellationTokenSource? = null

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<ViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getWeatherData()
        initIntentData()
        observeData()
        initRefreshLayout()
    }

    override fun onDestroy() {
        super.onDestroy()
        cancellationTokenSource?.cancel()
    }

    private fun initRefreshLayout() {
        cancellationTokenSource = CancellationTokenSource()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        binding.refresh.setOnRefreshListener {
            cancellationTokenSource?.let {
                DustUtil.fetchAirQualityData(this, viewModel, it, fusedLocationProviderClient)
            }
        }
    }

    private fun initIntentData() {
        stationName = intent.getStringExtra(SplashActivity.STATION_NAME) ?: ""
        addr = intent.getStringExtra(SplashActivity.ADDR) ?: ""
        viewModel.getMeasuredValue(stationName)
    }

    private fun observeData() {
        try {
            viewModel.stateLiveData.observe(this) {
                when (it) {
                    is State.SuccessMonitoringStation -> {
                        stationName = it.monitoringStation.stationName
                        viewModel.getMeasuredValue(stationName)
                    }

                    is State.SuccessMeasureVale -> {
                        displayAurQualityData(it.airQualityModel)
                        binding.progressBar.visibility = GONE
                        binding.refresh.isRefreshing = false
                    }
                    is State.SuccessWeatherValue -> {
                        Log.d("WeatherList", "${it.weatherList}")
                    }
                    else -> {
                        binding.contentsLayout.alpha = 0f
                        DialogUtil.showErrorDialog(this)
                    }
                }
            }
        } catch (e: Exception) {
            DialogUtil.showErrorDialog(this)
        }
    }

    private fun displayAurQualityData(airQualityModel: AirQualityModel) = with(binding) {
        contentsLayout.animate().alpha(1f).start()

        measuringStationNameTextView.text = stationName
        measuringStationAddressTextView.text = addr
        airQualityModel.khaiGrade.let { grade ->
            root.setBackgroundResource(grade.colorResId)

            totalGradeLabelTextView.text = grade.label
            totalGradeEmojiTextView.text = grade.emoji
        }

        with(airQualityModel) {
            fineDustInformationTextView.text = "미세먼지: $pm10Value ㎍/㎥ ${pm10Grade.emoji}"
            ultraFineDustInformationTextView.text = "초미세먼지: $pm25Value ㎍/㎥ ${pm25Grade.emoji}"

            with(so2Item) {
                labelTextView.text = "아황산가스"
                gradleTextView.text = so2Grade.toString()
                valueTextView.text = "$so2Value ppm"
            }
            with(coItem) {
                labelTextView.text = "일산화탄소"
                gradleTextView.text = coGrade.toString()
                valueTextView.text = "$coValue ppm"
            }
            with(o3Item) {
                labelTextView.text = "오존"
                gradleTextView.text = o3Grade.toString()
                valueTextView.text = "$o3Value ppm"
            }
            with(no2Item) {
                labelTextView.text = "이산화질소"
                gradleTextView.text = no2Grade.toString()
                valueTextView.text = "$no2Value ppm"
            }
        }
    }

    private fun getWeatherData() {
        val currentDateTime = getBaseDataTime()
        viewModel.getVillageForecast(currentDateTime.first, currentDateTime.second)
    }

    private fun getBaseDataTime(): Pair<String, String> {
        return Pair(getBaseDate(), getBaseTime())
    }

    private fun getBaseDate() = LocalDateTime.now().apply {
        if (isBefore(LocalTime.parse("02:10"))) {
            minusDays(1)
        }
    }.toString("yyyyMMdd")


    private fun getBaseTime(): String {
        val currentTime = LocalTime.now()
        return when {
            currentTime.isBefore(LocalTime.parse("02:10")) -> "2300"
            currentTime.isBefore(LocalTime.parse("05:10")) -> "0200"
            currentTime.isBefore(LocalTime.parse("08:10")) -> "0500"
            currentTime.isBefore(LocalTime.parse("11:10")) -> "0800"
            currentTime.isBefore(LocalTime.parse("14:10")) -> "1100"
            currentTime.isBefore(LocalTime.parse("17:10")) -> "1400"
            currentTime.isBefore(LocalTime.parse("20:10")) -> "1700"
            else -> "2000"
        }
    }

}