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
import com.github.educationissimple.components.composables.dialogs.DefaultDialog
import com.github.educationissimple.components.composables.inputs.DefaultRadioItem
import com.github.educationissimple.tasks.R
import com.github.educationissimple.tasks.domain.entities.SortType

/**
 * Displays a dialog for selecting the sorting type for tasks.
 *
 * The dialog allows the user to choose a sorting criterion for tasks:
 * - Sort by date (ascending or descending)
 * - Sort by priority
 * - Sort by text (alphabetically ascending or descending)
 *
 * @param onDismiss A callback function that is triggered when the dialog is dismissed.
 * @param onSortTypeChange A callback function triggered when the user selects a sort type. It receives the selected [SortType] as a parameter.
 * @param sortType The current sort type of the tasks. This value is used to highlight the selected sort type in the dialog.
 * @param modifier A [Modifier] to customize the appearance of the dialog.
 */
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
    val labelsByPriority = mapOf(
        SortType.DateAscending to stringResource(R.string.by_date_asc),
        SortType.DateDescending to stringResource(R.string.by_date_desc),
        SortType.Priority to stringResource(R.string.by_priority),
        SortType.TextAscending to stringResource(R.string.by_abc_asc),
        SortType.TextDescending to stringResource(R.string.by_abc_desc)
    )

    Column(horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()) {

        labelsByPriority.forEach { entry ->
            DefaultRadioItem(
                text = entry.value,
                selected = sortType == entry.key,
                onClick = {
                    onSortTypeChange(entry.key)
                }
            )
        }

        TextButton(onClick = onDismiss, modifier = Modifier.align(Alignment.End)) {
            Text(
                text = stringResource(R.string.ok),
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