package com.github.educationissimple.audio.domain.utils

fun timeChangeToPosition(currentTime: Long, duration: Long, timeToAddInSeconds: Long): Long {
    return (currentTime + timeToAddInSeconds * 1000).coerceIn(0, duration)
}
