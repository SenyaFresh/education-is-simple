package com.github.educationissimple.audio.presentation.utils

import java.util.Locale
import kotlin.time.Duration.Companion.seconds

fun formatDurationTime(durationSeconds: Long): String {
    return durationSeconds.seconds.toComponents { hours, minutes, seconds, _ ->
        val locale = Locale.getDefault()
        when {
            hours > 0 -> String.format(locale, "%d:%02d:%02d", hours, minutes, seconds)
            minutes > 0 -> String.format(locale, "%d:%02d", minutes, seconds)
            else -> String.format(locale, "0:%02d", seconds)
        }
    }
}