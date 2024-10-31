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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.github.educationissimple.audio.R
import com.github.educationissimple.audio.domain.entities.Audio
import com.github.educationissimple.components.colors.Highlight
import com.github.educationissimple.components.colors.Neutral

@Composable
fun CurrentAudioFloatingItem(
    audio: Audio,
    isPlaying: Boolean,
    onPlayClick: () -> Unit,
    onPauseClick: () -> Unit,
    onClose: () -> Unit,
    onClick: () -> Unit
) {

    Surface(
        color = Neutral.Light.Lightest,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Min),
        ) {
            IconButton(
                onClick = onClose,
                colors = IconButtonDefaults.iconButtonColors(contentColor = Neutral.Dark.Light),
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
                Text(text = audio.subtitle, fontSize = 10.sp, color = Neutral.Dark.Light)
            }

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = if (isPlaying) onPauseClick else onPlayClick,
                colors = IconButtonDefaults.iconButtonColors(contentColor = Highlight.Darkest)
            ) {
                if (isPlaying) {
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

@Preview
@Composable
fun CurrentAudioFloatingItemPreview() {
    CurrentAudioFloatingItem(
        audio = Audio(
            id = 0,
            categoryId = 0,
            imageRes = R.drawable.audio_image_preview,
            title = "Audio Title",
            subtitle = "Audio Subtitle",
            duration = 0
        ),
        isPlaying = true,
        onPlayClick = {},
        onPauseClick = {},
        onClose = {},
        onClick = {}
    )
}