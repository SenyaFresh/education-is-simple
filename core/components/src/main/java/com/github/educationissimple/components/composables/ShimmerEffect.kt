package com.github.educationissimple.components.composables

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize

/**
 * Adds a shimmer effect to the Modifier, typically used to create a loading animation
 * that simulates a shimmering light across a view. The effect is based on a gradient
 * that moves horizontally, creating a "shine" effect across the surface.
 *
 * This modifier can be applied to any Composable element to create a shimmer animation.
 *
 * @return The modified [Modifier] with a shimmer effect applied.
 */
fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition(label = "")

    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(animation = tween(1000)),
        label = ""
    )

    this
        .onGloballyPositioned { coordinates ->
            size = coordinates.size
        }
        .drawWithCache {
            val shimmerBrush = Brush.linearGradient(
                colors = listOf(
                    Color(0xAAB8B5B5),
                    Color(0xAA8F8B8B),
                    Color(0xAAB8B5B5)
                ),
                start = Offset(startOffsetX, 0f),
                end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
            )

            onDrawBehind {
                drawRect(shimmerBrush)
            }
        }
}