package com.github.educationissimple.audio_player.di

import kotlin.properties.Delegates.notNull

/**
 * [PlayerComponent] dependencies provider.
 */
interface PlayerDepsProvider {

    val deps: PlayerDeps

    companion object : PlayerDepsProvider by PlayerDepsStore

}

/**
 * [PlayerComponent] dependencies store.
 */
object PlayerDepsStore : PlayerDepsProvider {
    override var deps: PlayerDeps by notNull()
}
