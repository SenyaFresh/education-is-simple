package com.github.educationissimple.audio.di

import kotlin.properties.Delegates.notNull

interface AudioDepsProvider {

    val deps: AudioDeps

    companion object : AudioDepsProvider by AudioDepsStore

}

object AudioDepsStore : AudioDepsProvider {
    override var deps: AudioDeps by notNull()
}