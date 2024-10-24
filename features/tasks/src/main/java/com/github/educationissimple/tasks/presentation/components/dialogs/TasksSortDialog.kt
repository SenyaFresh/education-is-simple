package com.github.educationissimple.tasks.presentation.components.dialogs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.github.educationissimple.components.colors.Highlight
import com.github.educationissimple.components.colors.Neutral
import com.github.educationissimple.components.composables.DefaultDialog
import com.github.educationissimple.components.composables.DefaultRadioItem
import com.github.educationissimple.tasks.R
import com.github.educationissimple.tasks.domain.entities.SortType

@Composable
fun TasksSortDialog(
    onDismiss: () -> Unit,
    onSortTypeChange: (SortType) -> Unit,
    sortType: SortType?,
    modifier: Modifier = Modifier
) = DefaultDialog(
    onDismiss = onDismiss,
    title = stringResource(R.string.select_sort_type),
    modifier = modifier
) {
    Column(horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()) {
        DefaultRadioItem(
            text = stringResource(R.string.by_priority),
            selected = sortType is SortType.Priority,
            onClick = { onSortTypeChange(SortType.Priority) },
            textColor = Neutral.Dark.Darkest,
            selectedColor = Highlight.Darkest,
            unselectedColor = Neutral.Dark.Darkest
        )

        DefaultRadioItem(
            text = stringResource(R.string.by_date),
            selected = sortType is SortType.Date,
            onClick = { onSortTypeChange(SortType.Date) },
            textColor = Neutral.Dark.Darkest,
            selectedColor = Highlight.Darkest,
            unselectedColor = Neutral.Dark.Darkest
        )

        DefaultRadioItem(
            text = stringResource(R.string.by_abc),
            selected = sortType is SortType.Text,
            onClick = { onSortTypeChange(SortType.Text) },
            textColor = Neutral.Dark.Darkest,
            selectedColor = Highlight.Darkest,
            unselectedColor = Neutral.Dark.Darkest
        )

        TextButton(onClick = onDismiss, modifier = Modifier.align(Alignment.End)) {
            Text(
                text = stringResource(R.string.ok),
                color = Highlight.Darkest,
                fontSize = 16.sp
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun TasksSortDialogPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        TasksSortDialog(
            onDismiss = { },
            onSortTypeChange = { },
            sortType = SortType.Priority,
            modifier = Modifier
        )
    }
}