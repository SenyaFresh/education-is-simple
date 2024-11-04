package com.github.educationissimple.audio_player.di

import android.content.Context
import com.github.educationissimple.common.di.Player
import dagger.BindsInstance
import dagger.Component

@Player
@Component(modules = [PlayerModule::class])
internal interface PlayerComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun deps(deps: PlayerDeps): Builder

        fun build(): PlayerComponent
    }
}

interface PlayerDeps {
    val context: Context
}