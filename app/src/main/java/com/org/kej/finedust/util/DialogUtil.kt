package com.org.kej.finedust.util

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import com.org.kej.finedust.R
import com.org.kej.finedust.presenter.splash.SplashActivity

object DialogUtil {
    fun showErrorDialog(context: Context) {
        AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.error))
            .setIcon(R.drawable.fine_dust_app)
            .setMessage(context.getString(R.string.error_title))
            .setPositiveButton("종료") { _, _ ->
                (context as? Activity)?.finishAffinity()
            }
            .setNegativeButton("앱 재시도") { _, _ ->
                (context as? Activity)?.run {
                    finishAffinity()
                    startActivity(Intent(context, SplashActivity::class.java))
                }
            }.create().show()

    }
}