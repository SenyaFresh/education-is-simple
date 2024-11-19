package com.github.educationissimple.audio_player.di

import kotlin.properties.Delegates.notNull

/**
 * [PlayerComponent] dependencies provider.
 */
interface PlayerDepsProvider {

    /**
     * [PlayerComponent] dependencies.
     */
    val deps: PlayerDeps

    /**
     * Singleton instance of [PlayerDepsProvider] provided by [PlayerDepsStore].
     */
    companion object : PlayerDepsProvider by PlayerDepsStore

}

/**
 * [PlayerComponent] dependencies store.
 */
object PlayerDepsStore : PlayerDepsProvider {

    /**
     * [PlayerComponent] dependencies. Requires to be set before accessing them.
     */
    override var deps: PlayerDeps by notNull()
}
