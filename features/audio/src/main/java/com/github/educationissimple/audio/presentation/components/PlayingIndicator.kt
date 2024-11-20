package com.github.educationissimple.audio.presentation.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * A composable that displays a visual indicator to show whether an audio is currently playing. The indicator
 * animates with a resizing effect when the audio is playing and remains a fixed size when paused.
 *
 * @param isPlaying A boolean that indicates whether the audio is currently playing. The animation is active
 * when `isPlaying` is `true`, and the indicator remains static when `isPlaying` is `false`.
 * @param modifier An optional modifier to customize the appearance or behavior of the indicator.
 */
@Composable
fun PlayingIndicator(isPlaying: Boolean, modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "resizing")
    val sizeAnimation = infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(250, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "resizing"
    )

    val indicatorColor = MaterialTheme.colorScheme.primary
    Canvas(modifier = modifier
        .size(100.dp)
        .scale(if (isPlaying) sizeAnimation.value else 1f), onDraw = {
        drawCircle(color = indicatorColor)
    })
}

@Preview
@Composable
fun PlayingIndicatorPreview() {
    PlayingIndicator(true)
}