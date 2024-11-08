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
import com.github.educationissimple.components.colors.Support
import com.github.educationissimple.components.composables.dialogs.DefaultDialog
import com.github.educationissimple.components.composables.inputs.DefaultRadioItem
import com.github.educationissimple.tasks.R
import com.github.educationissimple.tasks.domain.entities.Task

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
            textColor = Support.Warning.Dark,
            selectedColor = Support.Warning.Dark,
            unselectedColor = Support.Warning.Dark
        )

        DefaultRadioItem(
            text = stringResource(R.string.medium_priority),
            selected = priority == Task.Priority.SecondaryPriority,
            onClick = { onPriorityChange(Task.Priority.SecondaryPriority) },
            textColor = Support.Warning.Medium,
            selectedColor = Support.Warning.Medium,
            unselectedColor = Support.Warning.Medium
        )

        DefaultRadioItem(
            text = stringResource(R.string.low_priority),
            selected = priority == Task.Priority.NoPriority,
            onClick = { onPriorityChange(Task.Priority.NoPriority) },
            textColor = Neutral.Dark.Lightest,
            selectedColor = Neutral.Dark.Lightest,
            unselectedColor = Neutral.Dark.Lightest
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
fun TaskPriorityDialogPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        TaskPriorityDialog(
            onDismiss = { },
            onPriorityChange = { },
            priority = Task.Priority.TopPriority
        )
    }
}