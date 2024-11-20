package com.github.educationissimple.audio.domain.utils

/**
 * Adjusts the current time by adding a specified number of seconds, ensuring that the result
 * stays within the bounds of the total duration.
 *
 * @param currentTime The current time in milliseconds.
 * @param duration The total duration in milliseconds.
 * @param timeToAddInSeconds The amount of time to add (in seconds).
 * @return The adjusted time in milliseconds, constrained between 0 and the duration.
 */
fun timeChangeToPosition(currentTime: Long, duration: Long, timeToAddInSeconds: Long): Long {
    return (currentTime + timeToAddInSeconds * 1000).coerceIn(0, duration)
}
