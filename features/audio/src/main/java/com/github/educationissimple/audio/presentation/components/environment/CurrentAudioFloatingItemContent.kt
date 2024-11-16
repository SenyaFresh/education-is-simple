package com.github.educationissimple.audio.presentation.components.environment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.educationissimple.audio.R
import com.github.educationissimple.audio.di.AudioDiContainer
import com.github.educationissimple.audio.di.rememberAudioDiContainer
import com.github.educationissimple.audio.domain.entities.Audio
import com.github.educationissimple.audio.domain.entities.AudioListState
import com.github.educationissimple.audio.domain.entities.PlayerController
import com.github.educationissimple.audio.presentation.entities.dummies.dummyAudio
import com.github.educationissimple.audio.presentation.events.AudioEvent
import com.github.educationissimple.audio.presentation.viewmodels.AudioViewModel
import com.github.educationissimple.common.ResultContainer

@Composable
fun CurrentAudioFloatingItem(
    onStopAudioService: () -> Unit,
    diContainer: AudioDiContainer = rememberAudioDiContainer(),
    viewModel: AudioViewModel = viewModel(
        factory = diContainer.viewModelFactory.create(
            onStopAudioService = onStopAudioService
        )
    )
) {
    val audioListState by viewModel.audioListState.collectAsStateWithLifecycle()

    if (audioListState is ResultContainer.Done) {
        CurrentAudioFloatingItemContent(
            audio = viewModel.currentAudioItem.collectAsStateWithLifecycle().value,
            state = audioListState.unwrap(),
            onPlaylistController = { viewModel.onEvent(AudioEvent.PlayerEvent(it)) },
            onAudioDelete = {
                audioListState.unwrapOrNull()?.currentAudioUri?.let {
                    viewModel.onEvent(AudioEvent.DeleteAudioItemEvent(it))
                }
            }
        )
    }
}

@Composable
fun CurrentAudioFloatingItemContent(
    audio: Audio?,
    state: AudioListState,
    onPlaylistController: (PlayerController) -> Unit,
    onAudioDelete: () -> Unit
) {
    var showPlaylistController by remember { mutableStateOf(false) }

    if (state.state != AudioListState.State.IDLE && audio != null) {
        if (showPlaylistController) {
            AudioSheet(
                audio = audio,
                isPlaying = state.state == AudioListState.State.AUDIO_PLAYING,
                isSheetOpen = showPlaylistController,
                onPlayerController = onPlaylistController,
                onDeleteClick = {
                    showPlaylistController = false
                    onAudioDelete()
                },
                currentTime = state.positionMs,
                onDismiss = { showPlaylistController = false }
            )
        }
        Column {
            HorizontalDivider(thickness = 1.dp)
            Surface(
                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                onClick = { showPlaylistController = true }
            ) {
                Row(
                    modifier = Modifier.height(IntrinsicSize.Min),
                ) {
                    IconButton(
                        onClick = { onPlaylistController(PlayerController.Close) },
                        colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.onSurfaceVariant),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(R.string.close_audio)
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Column(
                        Modifier.fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = audio.title)
                        Text(
                            text = audio.subtitle,
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    IconButton(
                        onClick = { onPlaylistController(PlayerController.PlayPause) },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    ) {
                        if (state.state == AudioListState.State.AUDIO_PLAYING) {
                            Icon(
                                imageVector = Icons.Default.Pause,
                                contentDescription = stringResource(R.string.audio_pause)
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = stringResource(R.string.audio_play)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CurrentAudioFloatingItemPreview() {
    CurrentAudioFloatingItemContent(
        audio = dummyAudio,
        state = AudioListState(AudioListState.State.AUDIO_PLAYING, "", 0, 0),
        onPlaylistController = {},
        onAudioDelete = {}
    )
}