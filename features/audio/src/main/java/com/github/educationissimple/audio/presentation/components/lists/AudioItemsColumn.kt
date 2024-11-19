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
    audioItems: ResultContainer<List<Audio>>,
    onReloadAudioItems: () -> Unit,
    selectedAudioUri: String? = null,
    playingAudioUri: String? = null,
    onAudioClick: (String) -> Unit,
    onAudioDelete: (String) -> Unit
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
        onTryAgain = onReloadAudioItems
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(LocalSpacing.current.medium),
            verticalArrangement = Arrangement.spacedBy(
                LocalSpacing.current.small
            )
        ) {
            items(audioItems.unwrap(), key = { it.uri }) { audio ->
                AudioListItem(
                    audio = audio,
                    onClick = { onAudioClick(audio.uri) },
                    onAudioDelete = { onAudioDelete(audio.uri) },
                    isAudioPlaying = audio.uri == playingAudioUri,
                    isSelected = audio.uri == selectedAudioUri
                )
            }
        }
    }
}