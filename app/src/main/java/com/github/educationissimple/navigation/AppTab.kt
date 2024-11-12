package com.github.educationissimple.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FormatListBulleted
import androidx.compose.material.icons.filled.Headset
import androidx.compose.ui.graphics.vector.ImageVector
import com.github.educationissimple.R
import kotlinx.collections.immutable.persistentListOf

data class AppTab(
    val imageVector: ImageVector,
    @StringRes val titleRes: Int,
    val graphRoute: Any
)

val MainTabs = persistentListOf(
    AppTab(
        imageVector = Icons.AutoMirrored.Filled.FormatListBulleted,
        titleRes = R.string.tasks,
        graphRoute = TasksGraph
    ),
    AppTab(
        imageVector = Icons.Default.Headset,
        titleRes = R.string.audio,
        graphRoute = AudioGraph
    )
)