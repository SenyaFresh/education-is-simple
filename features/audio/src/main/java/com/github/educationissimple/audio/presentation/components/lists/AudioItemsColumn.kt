package com.github.educationissimple.audio.presentation.components.lists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.educationissimple.audio.domain.entities.Audio
import com.github.educationissimple.audio.presentation.components.items.AudioListItem
import com.github.educationissimple.audio.presentation.components.items.LoadingAudioListItem
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.presentation.ResultContainerComposable
import com.github.educationissimple.presentation.locals.LocalSpacing

@Composable
fun AudioItemsColumn(
    audioItems: ResultContainer<List<Audio>>
) {
    ResultContainerComposable(
        container = audioItems,
        onLoading = {
            Column(
                modifier = Modifier
                    .padding(LocalSpacing.current.medium)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(
                    LocalSpacing.current.small
                )
            ) {
                repeat(7) {
                    LoadingAudioListItem()
                }
            }
        },
        onTryAgain = { }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(LocalSpacing.current.medium),
            verticalArrangement = Arrangement.spacedBy(
                LocalSpacing.current.small
            )
        ) {
            items(audioItems.unwrap(), key = { it.id }) { audio ->
                AudioListItem(
                    audio = audio,
                    onClick = { },
                    onMenuClick = { },
                    isAudioPlaying = false,
                    isSelected = false
                )
            }
        }
    }
}