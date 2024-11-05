package com.github.educationissimple

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.github.educationissimple.audio_player.services.AudioServiceImpl
import com.github.educationissimple.navigation.AppNavigation
import com.github.educationissimple.ui.theme.EducationIsSimpleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startAudioService()
        setContent {
            EducationIsSimpleTheme {
                AppNavigation()
            }
        }
    }

    private fun startAudioService() {
        val intent = Intent(this, AudioServiceImpl::class.java) // todo: it must be interface
        startForegroundService(intent)
    }
}
