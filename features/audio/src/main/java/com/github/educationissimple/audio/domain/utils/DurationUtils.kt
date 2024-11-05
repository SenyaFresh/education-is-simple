package com.github.educationissimple.audio.domain.utils

fun timeChangeToPosition(currentTime: Long, duration: Long, timeToAdd: Long): Long {
    return (currentTime + timeToAdd).coerceIn(0, duration)
}
