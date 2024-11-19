package com.github.educationissimple

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.github.educationissimple.audio_player.services.AudioServiceManager
import com.github.educationissimple.navigation.AppNavigation
import com.github.educationissimple.ui.theme.EducationIsSimpleTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var audioServiceManager: AudioServiceManager

    private var isServiceRunning: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as BaseApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContent {
            EducationIsSimpleTheme {
                AppNavigation(
                    onStartAudioService = ::onStartService,
                    onStopAudioService = ::onStopService
                )
            }
        }
    }

    private fun onStartService() {
        if (!isServiceRunning) {
            val intent = Intent(this, audioServiceManager.getServiceClass().java)
            startForegroundService(intent)
            isServiceRunning = true
        }
    }

    private fun onStopService() {
        if (isServiceRunning) {
            val intent = Intent(this, audioServiceManager.getServiceClass().java)
            stopService(intent)
            isServiceRunning = false
        }
    }
}
