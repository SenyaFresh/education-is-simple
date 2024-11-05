package com.github.educationissimple.audio.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import com.github.educationissimple.audio.presentation.viewmodels.AudioViewModel
import javax.inject.Inject

@Stable
class AudioDiContainer {
    @Inject
    lateinit var viewModelFactory: AudioViewModel.Factory
}

@Composable
fun rememberAudioDiContainer(): AudioDiContainer {
    return remember {
        AudioDiContainer().also {
            DaggerAudioComponent.builder().deps(AudioDepsProvider.deps).build().inject(it)
        }
    }
}