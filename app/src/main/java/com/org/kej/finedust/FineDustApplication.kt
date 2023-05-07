package com.org.kej.finedust

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class FineDustApplication @Inject constructor(): Application() {}