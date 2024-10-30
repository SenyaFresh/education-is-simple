package com.github.educationissimple.navigation

import androidx.annotation.StringRes
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.github.educationissimple.components.colors.Highlight
import com.github.educationissimple.components.colors.Neutral

sealed class LeftIconAction {
    data object None : LeftIconAction()
    data class Visible(val imageVector: ImageVector, val onClick: () -> Unit) : LeftIconAction()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    @StringRes titleRes: Int? = null,
    leftIconAction: LeftIconAction
) {
    TopAppBar(
        title = {
            titleRes?.let {
                Text(
                    text = stringResource(it),
                    fontWeight = FontWeight.Medium,
                )
            }
        },
        navigationIcon = {
            if (leftIconAction is LeftIconAction.Visible) {
                IconButton(onClick = leftIconAction.onClick) {
                    Icon(
                        imageVector = leftIconAction.imageVector,
                        tint = Highlight.Dark,
                        contentDescription = null
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Neutral.Light.Lightest)
    )
}