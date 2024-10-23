package com.github.educationissimple.tasks.presentation.components.items

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.educationissimple.components.colors.Highlight
import com.github.educationissimple.components.colors.Neutral
import com.github.educationissimple.components.colors.Support
import com.github.educationissimple.tasks.R
import com.github.educationissimple.tasks.domain.entities.Task
import com.github.educationissimple.tasks.presentation.components.dialogs.TaskPriorityDialog
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun TaskCard(
    task: Task,
    onTaskCompletionChange: (Boolean) -> Unit,
    onTaskDelete: () -> Unit,
    onPriorityChange: (Task.Priority) -> Unit,
    modifier: Modifier = Modifier,
    isActionsRevealed: Boolean = false
) {
    var isTaskCompleted by remember { mutableStateOf(task.isCompleted) }
    var showTaskPriorityDialog by remember { mutableStateOf(false) }

    val priorityColor = when (task.priority) {
        Task.Priority.TopPriority -> Support.Warning.Dark
        Task.Priority.SecondaryPriority -> Support.Warning.Medium
        Task.Priority.NoPriority -> Neutral.Dark.Lightest
    }

    var contextMenuWidth by remember {
        mutableFloatStateOf(0f)
    }

    val offset = remember {
        Animatable(0f)
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = isActionsRevealed, contextMenuWidth) {
        if (isActionsRevealed) {
            offset.animateTo(-contextMenuWidth)
        } else {
            offset.animateTo(0f)
        }
    }

    if (showTaskPriorityDialog) {
        TaskPriorityDialog(
            onDismiss = { showTaskPriorityDialog = false },
            onPriorityChange = onPriorityChange,
            priority = task.priority
        )
    }

    Card(
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(if (task.date != null) 70.dp else 60.dp)
        ) {
            // Task actions.
            Row(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .onSizeChanged {
                        contextMenuWidth = it.width.toFloat()
                    },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Change task priority action.
                TaskActionIcon(
                    imageVector = Icons.Default.Star,
                    text = stringResource(R.string.priority),
                    contentColor = Neutral.Light.Lightest,
                    containerColor = priorityColor,
                    modifier = Modifier.fillMaxHeight(),
                    onClick = { showTaskPriorityDialog = true }
                )

                // Delete task action.
                TaskActionIcon(
                    imageVector = Icons.Default.Delete,
                    text = stringResource(R.string.delete),
                    contentColor = Neutral.Light.Lightest,
                    containerColor = Support.Error.Dark,
                    modifier = Modifier.fillMaxHeight(),
                    onClick = onTaskDelete
                )
            }

            // Task content.
            Surface(
                color = if (isTaskCompleted) Highlight.Lightest else Neutral.Light.Lightest,
                modifier = Modifier
                    .offset { IntOffset(offset.value.roundToInt(), 0) }
                    .pointerInput(contextMenuWidth) {
                        detectHorizontalDragGestures(
                            onHorizontalDrag = { _, dragAmount ->
                                scope.launch {
                                    val newOffset = (offset.value + dragAmount)
                                        .coerceIn(-contextMenuWidth, 0f)
                                    offset.snapTo(newOffset)
                                }
                            },
                            onDragEnd = {
                                when {
                                    offset.value <= -contextMenuWidth / 2f -> {
                                        scope.launch {
                                            offset.animateTo(-contextMenuWidth)
                                        }
                                    }

                                    else -> {
                                        scope.launch {
                                            offset.animateTo(0f)
                                        }
                                    }
                                }
                            }
                        )
                    }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(8.dp)
                            .background(color = priorityColor)
                    )

                    Checkbox(
                        checked = isTaskCompleted,
                        onCheckedChange = { isChecked ->
                            isTaskCompleted = isChecked
                            onTaskCompletionChange(isChecked)
                        },
                        colors = CheckboxDefaults.colors(
                            uncheckedColor = Neutral.Light.Darkest,
                            checkedColor = Highlight.Darkest
                        ),
                        modifier = Modifier.scale(0.9f)
                    )

                    Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                        Text(
                            text = task.text,
                            maxLines = 1,
                            fontWeight = FontWeight.Medium,
                            overflow = TextOverflow.Ellipsis,
                            style = TextStyle(textDecoration = if (isTaskCompleted) TextDecoration.LineThrough else null),
                        )
                        task.date?.let {
                            Text(
                                text = task.date,
                                fontSize = 12.sp,
                                color = Neutral.Dark.Light
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun TaskCardPreview() {
    Column(modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {

        TaskCard(
            Task(id = 1, text = "Go to work", isCompleted = true, date = "10-08", priority = Task.Priority.TopPriority),
            onTaskCompletionChange = { _ -> },
            onTaskDelete = { },
            onPriorityChange = { _ -> },
        )

        TaskCard(
            Task(
                id = 2,
                text = "Task with long long long long long long long long long long long long",
                date = "10-08"
            ),
            onTaskCompletionChange = { _ -> },
            onTaskDelete = { },
            onPriorityChange = { _ -> },
        )
    }
}