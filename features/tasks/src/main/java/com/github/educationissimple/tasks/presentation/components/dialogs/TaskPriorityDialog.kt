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
import com.github.educationissimple.tasks.domain.entities.Task
import com.github.educationissimple.tasks.presentation.utils.NoPriorityColor
import com.github.educationissimple.tasks.presentation.utils.SecondaryPriorityColor
import com.github.educationissimple.tasks.presentation.utils.TopPriorityColor

/**
 * Displays a dialog for selecting the priority of a task.
 *
 * The dialog allows the user to choose between three priority levels for a task:
 * top priority, secondary priority, and no priority.
 * It provides options to either select a priority or cancel the dialog.
 *
 * @param onDismiss A callback function triggered when the dialog is dismissed.
 * @param onPriorityChange A callback function triggered when the user selects a priority. It receives the selected [Task.Priority] as a parameter.
 * @param priority The current priority of the task. This is used to highlight the selected priority in the dialog.
 * @param modifier A [Modifier] to customize the appearance of the dialog.
 */
@Composable
fun TaskPriorityDialog(
    onDismiss: () -> Unit,
    onPriorityChange: (Task.Priority) -> Unit,
    priority: Task.Priority,
    modifier: Modifier = Modifier
) = DefaultDialog(
    onDismiss = onDismiss,
    title = stringResource(R.string.select_task_priority),
    modifier = modifier
) {
    Column(horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()) {
        DefaultRadioItem(
            text = stringResource(R.string.highest_priority),
            selected = priority == Task.Priority.TopPriority,
            onClick = { onPriorityChange(Task.Priority.TopPriority) },
            textColor = TopPriorityColor,
            selectedColor = TopPriorityColor,
            unselectedColor = TopPriorityColor
        )

        DefaultRadioItem(
            text = stringResource(R.string.medium_priority),
            selected = priority == Task.Priority.SecondaryPriority,
            onClick = { onPriorityChange(Task.Priority.SecondaryPriority) },
            textColor = SecondaryPriorityColor,
            selectedColor = SecondaryPriorityColor,
            unselectedColor = SecondaryPriorityColor
        )

        DefaultRadioItem(
            text = stringResource(R.string.low_priority),
            selected = priority == Task.Priority.NoPriority,
            onClick = { onPriorityChange(Task.Priority.NoPriority) },
            textColor = NoPriorityColor,
            selectedColor = NoPriorityColor,
            unselectedColor = NoPriorityColor
        )

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
fun TaskPriorityDialogPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        TaskPriorityDialog(
            onDismiss = { },
            onPriorityChange = { },
            priority = Task.Priority.TopPriority
        )
    }
}