package com.github.educationissimple.audio.domain.utils

fun timeChangeToPosition(currentTime: Long, duration: Long, timeToAdd: Long): Float {
    val newPosition = (currentTime + timeToAdd).coerceIn(0, duration)
    return newPosition.toFloat() / duration.toFloat()
}
