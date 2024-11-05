package com.github.educationissimple.audio.di

import com.github.educationissimple.audio.domain.handlers.AudioListHandler
import com.github.educationissimple.audio.domain.repositories.AudioRepository
import com.github.educationissimple.common.di.Feature
import dagger.BindsInstance
import dagger.Component

@Feature
@Component(modules = [AudioModule::class])
internal interface AudioComponent {

    fun inject(it: AudioDiContainer)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun deps(deps: AudioDeps): Builder

        fun build(): AudioComponent
    }

}

interface AudioDeps {
    val audioRepository: AudioRepository
    val audioHandler: AudioListHandler
}