package com.github.educationissimple.audio.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.github.educationissimple.audio.domain.entities.Audio
import com.github.educationissimple.audio.domain.entities.AudioCategory
import com.github.educationissimple.common.ResultContainer

@Composable
fun AudioListScreen(
    audioCategories: ResultContainer<List<AudioCategory>>,
    audioItems: ResultContainer<List<Audio>>,
    ) {

    Column {
        
    }

}