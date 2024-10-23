package com.github.educationissimple.tasks.presentation.components.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.educationissimple.components.colors.Highlight
import com.github.educationissimple.components.colors.Neutral
import com.github.educationissimple.components.colors.Support
import com.github.educationissimple.tasks.R
import com.github.educationissimple.tasks.domain.entities.Task

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskPriorityDialog(
    onDismiss: () -> Unit,
    onPriorityChange: (Task.Priority) -> Unit,
    priority: Task.Priority,
    modifier: Modifier = Modifier
) {
    BasicAlertDialog(
        onDismissRequest = onDismiss,
        modifier = modifier,
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            tonalElevation = AlertDialogDefaults.TonalElevation,
            shape = RoundedCornerShape(12.dp),
            color = Neutral.Light.Lightest
        ) {
            Column(horizontalAlignment = Alignment.Start, modifier = Modifier.padding(12.dp)) {
                Text(
                    text = stringResource(R.string.select_tast_priority),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(14.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onPriorityChange(Task.Priority.TopPriority) }
                ) {
                    RadioButton(
                        selected = priority == Task.Priority.TopPriority,
                        colors = RadioButtonDefaults.colors(
                            unselectedColor = Support.Warning.Dark,
                            selectedColor = Support.Warning.Dark
                        ),
                        onClick = { onPriorityChange(Task.Priority.TopPriority) }
                    )
                    Text(
                        text = stringResource(R.string.highest_priority),
                        color = Support.Warning.Dark
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onPriorityChange(Task.Priority.SecondaryPriority) }
                ) {
                    RadioButton(
                        selected = priority == Task.Priority.SecondaryPriority,
                        colors = RadioButtonDefaults.colors(
                            unselectedColor = Support.Warning.Medium,
                            selectedColor = Support.Warning.Medium
                        ),
                        onClick = { onPriorityChange(Task.Priority.SecondaryPriority) }
                    )
                    Text(
                        text = stringResource(R.string.medium_priority),
                        color = Support.Warning.Medium
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onPriorityChange(Task.Priority.NoPriority) }
                ) {
                    RadioButton(
                        selected = priority == Task.Priority.NoPriority,
                        colors = RadioButtonDefaults.colors(
                            unselectedColor = Neutral.Dark.Lightest,
                            selectedColor = Neutral.Dark.Lightest
                        ),
                        onClick = { onPriorityChange(Task.Priority.NoPriority) }
                    )
                    Text(
                        text = stringResource(R.string.low_priority),
                        color = Neutral.Dark.Lightest
                    )
                }

                TextButton(onClick = onDismiss, modifier = Modifier.align(Alignment.End)) {
                    Text(
                        text = stringResource(R.string.ok),
                        color = Highlight.Darkest,
                        fontSize = 16.sp
                    )
                }
            }
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