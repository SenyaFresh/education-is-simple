package com.github.educationissimple.audio.presentation.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.educationissimple.components.colors.Support

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

    Canvas(modifier = modifier
        .size(100.dp)
        .scale(if (isPlaying) sizeAnimation.value else 1f), onDraw = {
        drawCircle(color = Support.Warning.Medium)
    })
}

@Preview
@Composable
fun PlayingIndicatorPreview() {
    PlayingIndicator(true)
}