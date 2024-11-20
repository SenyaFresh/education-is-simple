package com.github.educationissimple.audio.presentation.utils

import java.util.Locale
import kotlin.time.Duration.Companion.seconds

/**
 * Formats a duration in milliseconds to a human-readable time format (HH:MM:SS or MM:SS).
 *
 * This function takes a time duration in milliseconds and converts it into a string representation
 * in the format of hours, minutes, and seconds. The formatting will adjust based on the value of hours and minutes:
 * - If the duration is over an hour, it will display as "HH:MM:SS".
 * - If the duration is under an hour but over a minute, it will display as "MM:SS".
 * - If the duration is under a minute, it will display as "0:SS".
 *
 * @param durationMs The duration in milliseconds to format.
 * @return A string representing the formatted time in the format HH:MM:SS, MM:SS, or 0:SS.
 */
fun formatDurationTime(durationMs: Long): String {
    return (durationMs / 1000).seconds.toComponents { hours, minutes, seconds, _ ->
        val locale = Locale.getDefault()
        when {
            hours > 0 -> String.format(locale, "%d:%02d:%02d", hours, minutes, seconds)
            minutes > 0 -> String.format(locale, "%d:%02d", minutes, seconds)
            else -> String.format(locale, "0:%02d", seconds)
        }
    }
}