package com.github.educationissimple.tasks.presentation.components.environment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.github.educationissimple.components.colors.Neutral
import com.github.educationissimple.presentation.locals.LocalSpacing
import com.github.educationissimple.tasks.R

@Composable
fun TasksListActionsDropdownMenu(
    onSortTypeItemClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onFindItemClick: () -> Unit = {}
) = Box {
    var showDropdownMenu by remember { mutableStateOf(false) }

    IconButton(
        enabled = enabled,
        onClick = { showDropdownMenu = true },
        colors = IconButtonDefaults.iconButtonColors(contentColor = Neutral.Dark.Light),
        modifier = modifier
    ) {
        Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
    }
    DropdownMenu(
        expanded = showDropdownMenu,
        onDismissRequest = { showDropdownMenu = false },
        offset = DpOffset(LocalSpacing.current.medium, 0.dp),
        modifier = Modifier.background(color = Neutral.Light.Light)
    ) {
        DropdownMenuItem(
            text = { Text(stringResource(R.string.edit_categories)) },
            onClick = { }
        )

        DropdownMenuItem(
            text = { Text(stringResource(R.string.find)) },
            onClick = {
                onFindItemClick()
                showDropdownMenu = false
            }
        )

        DropdownMenuItem(
            text = { Text(stringResource(R.string.to_select_sort_type)) },
            onClick = {
                onSortTypeItemClick()
                showDropdownMenu = false
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TasksListActionsDropdownMenuPreview() {
    TasksListActionsDropdownMenu(onSortTypeItemClick = {})
}