package com.github.educationissimple.audio.di

import kotlin.properties.Delegates.notNull

/**
 * [AudioComponent] dependencies provider.
 */
interface AudioDepsProvider {

    /**
     * [AudioComponent] dependencies.
     */
    val deps: AudioDeps

    /**
     * Singleton instance of [AudioDepsProvider] provided by [AudioDepsStore].
     */
    companion object : AudioDepsProvider by AudioDepsStore

}

/**
 * [AudioComponent] dependencies store.
 */
object AudioDepsStore : AudioDepsProvider {
    override var deps: AudioDeps by notNull()
}