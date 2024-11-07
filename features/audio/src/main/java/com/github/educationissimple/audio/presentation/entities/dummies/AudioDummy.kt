package com.github.educationissimple.audio.presentation.entities.dummies

import android.graphics.Bitmap
import com.github.educationissimple.audio.domain.entities.Audio

val dummyAudio = Audio(
    uri = "",
    categoryId = 0,
    imageBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888),
    title = "Title",
    subtitle = "Subtitle",
    duration = 100
)