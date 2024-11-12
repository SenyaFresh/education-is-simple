package com.github.educationissimple.audio_player.services

import android.app.Service
import kotlin.reflect.KClass

interface AudioServiceManager {

    fun getService(): Service

    fun getServiceClass(): KClass<out Service>

}