package com.github.educationissimple.navigation

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class NavigateUpAction {
    data object None : NavigateUpAction()
    data class Visible(val onClick: () -> Unit) : NavigateUpAction()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    visible: Boolean = true,
    @StringRes titleRes: Int? = null,
    navigationUpAction: NavigateUpAction
) {
    AnimatedVisibility (visible && titleRes != null) {
        CenterAlignedTopAppBar(
            title = { titleRes?.let { Text(stringResource(it)) } },
            navigationIcon = {
                if (navigationUpAction is NavigateUpAction.Visible) {
                    IconButton(onClick = navigationUpAction.onClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            }
        )
    }
}