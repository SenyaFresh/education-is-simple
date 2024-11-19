package com.github.educationissimple.audio_player.services

import android.app.Service
import kotlin.reflect.KClass

interface AudioServiceManager {

    /**
     * Returns the [Service] associated with this manager.
     */
    fun getService(): Service

    /**
     * Returns the [KClass] of the [Service] associated with this manager.
     */
    fun getServiceClass(): KClass<out Service>

}