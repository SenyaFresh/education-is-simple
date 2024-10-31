package com.github.educationissimple.audio.presentation.components.environment

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Forward10
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PauseCircle
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material.icons.filled.Replay10
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import androidx.compose.ui.unit.sp
import com.github.educationissimple.audio.R
import com.github.educationissimple.audio.domain.entities.Audio
import com.github.educationissimple.audio.presentation.utils.formatDurationTime
import com.github.educationissimple.components.colors.Highlight
import com.github.educationissimple.components.colors.Neutral
import com.github.educationissimple.presentation.locals.LocalSpacing
import kotlin.math.round

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AudioSheet(
    audio: Audio,
    isPlaying: Boolean,
    currentTime: Long,
    onPlayClick: () -> Unit,
    onPauseClick: () -> Unit,
    onNextClick: () -> Unit,
    onPreviousClick: () -> Unit,
    onTimeChange: (Long) -> Unit,
    onMenuClick: () -> Unit,
    isSheetOpen: Boolean,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val musicProgress = remember { Animatable(currentTime.toFloat() / audio.duration.toFloat()) }
    val interactionSource = remember { MutableInteractionSource() }
    val thumbSize = DpSize(14.dp, 14.dp)

    LaunchedEffect(currentTime) {
        musicProgress.animateTo(currentTime.toFloat() / audio.duration.toFloat())
    }

    LaunchedEffect(isSheetOpen) {
        if (isSheetOpen) {
            sheetState.expand()
        } else {
            sheetState.hide()
        }
    }

    ModalBottomSheet(sheetState = sheetState, onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(LocalSpacing.current.large),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AudioImage(audio)
            Spacer(modifier = Modifier.size(LocalSpacing.current.large))
            AudioTitle(audio, onMenuClick)
            Spacer(modifier = Modifier.size(24.dp))
            AudioSlider(audio, musicProgress.value, currentTime, interactionSource, thumbSize, onTimeChange)
            Spacer(modifier = Modifier.height(LocalSpacing.current.extraLarge))
            AudioControls(isPlaying, onPlayClick, onPauseClick, onNextClick, onPreviousClick, onTimeChange)
            Spacer(modifier = Modifier.height(LocalSpacing.current.large))
        }
    }
}

@Composable
fun AudioImage(audio: Audio) {
    Image(
        painter = painterResource(id = audio.imageRes),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
    )
}

@Composable
fun AudioTitle(audio: Audio, onMenuClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Column {
            Text(text = audio.title, fontSize = 16.sp)
            Text(text = audio.subtitle, fontSize = 12.sp, color = Neutral.Dark.Light)
        }

        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = onMenuClick, modifier = Modifier.size(24.dp)) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = stringResource(R.string.audio_menu)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AudioSlider(
    audio: Audio,
    musicProgress: Float,
    currentTime: Long,
    interactionSource: MutableInteractionSource,
    thumbSize: DpSize,
    onTimeChange: (Long) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Slider(
            value = musicProgress,
            onValueChange = {
                onTimeChange(round(it * audio.duration).toLong())
            },
            valueRange = 0f..1f,
            colors = SliderDefaults.colors(
                activeTrackColor = Highlight.Darkest,
                inactiveTrackColor = Neutral.Light.Medium
            ),
            interactionSource = interactionSource,
            thumb = {
                SliderDefaults.Thumb(
                    interactionSource = interactionSource,
                    thumbSize = thumbSize,
                    colors = SliderDefaults.colors(thumbColor = Highlight.Darkest),
                    modifier = Modifier.offset {
                        if (thumbSize.height < 20.dp || thumbSize.width < 20.dp) {
                            val offsetX = (20.dp - thumbSize.width).roundToPx() / 2
                            val offsetY = (20.dp - thumbSize.height).roundToPx() / 2
                            IntOffset(offsetX, offsetY)
                        } else {
                            IntOffset(0, 0)
                        }
                    }
                )
            },
            modifier = Modifier.layout { measurable, constraints ->
                val placeable = measurable.measure(constraints.offset((8 * 2).dp.roundToPx()))
                layout(placeable.width, placeable.height) { placeable.place(0, 0) }
            }
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-12).dp)
        ) {
            Text(text = formatDurationTime(currentTime), color = Neutral.Dark.Light)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = formatDurationTime(audio.duration), color = Neutral.Dark.Light)
        }
    }
}

@Composable
fun AudioControls(
    isPlaying: Boolean,
    onPlayClick: () -> Unit,
    onPauseClick: () -> Unit,
    onNextClick: () -> Unit,
    onPreviousClick: () -> Unit,
    onTimeChange: (Long) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onTimeChange(-10) }, modifier = Modifier.scale(1.6f)) {
            Icon(imageVector = Icons.Default.Replay10, contentDescription = null)
        }

        IconButton(onClick = onPreviousClick, modifier = Modifier.scale(1.8f)) {
            Icon(imageVector = Icons.Default.SkipPrevious, contentDescription = null)
        }

        IconButton(onClick = if (isPlaying) onPauseClick else onPlayClick, modifier = Modifier.scale(2.6f)) {
            if (isPlaying) {
                Icon(imageVector = Icons.Default.PauseCircle, contentDescription = null)
            } else {
                Icon(imageVector = Icons.Default.PlayCircleFilled, contentDescription = null)
            }
        }

        IconButton(onClick = onNextClick, modifier = Modifier.scale(1.8f)) {
            Icon(imageVector = Icons.Default.SkipNext, contentDescription = null)
        }

        IconButton(onClick = { onTimeChange(10) }, modifier = Modifier.scale(1.6f)) {
            Icon(imageVector = Icons.Default.Forward10, contentDescription = null)
        }
    }
}

@Preview
@Composable
fun AudioSheetPreview() {
    AudioSheet(
        audio = Audio(
            id = 0,
            categoryId = 0,
            imageRes = R.drawable.audio_image_preview,
            title = "Audio Title",
            subtitle = "Audio Subtitle",
            duration = 100
        ),
        isPlaying = true,
        currentTime = 10,
        onPlayClick = {},
        onPauseClick = {},
        onNextClick = {},
        onPreviousClick = {},
        onMenuClick = {},
        onTimeChange = {},
        isSheetOpen = true,
        onDismiss = {}
    )
}