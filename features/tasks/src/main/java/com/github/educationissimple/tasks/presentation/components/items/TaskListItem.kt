package com.github.educationissimple.tasks.presentation.components.items

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.educationissimple.components.colors.Highlight
import com.github.educationissimple.components.colors.Neutral
import com.github.educationissimple.components.colors.Support
import com.github.educationissimple.presentation.locals.LocalSpacing
import com.github.educationissimple.tasks.R
import com.github.educationissimple.tasks.domain.entities.Task
import com.github.educationissimple.tasks.presentation.components.dialogs.TaskPriorityDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun TaskListItem(
    task: Task,
    onTaskCompletionChange: (Boolean) -> Unit,
    onTaskDelete: () -> Unit,
    onPriorityChange: (Task.Priority) -> Unit,
    modifier: Modifier = Modifier,
    isActionsRevealed: Boolean = false
) {
    var isTaskCompleted by remember { mutableStateOf(task.isCompleted) }
    var showTaskPriorityDialog by remember { mutableStateOf(false) }
    var contextMenuWidth by remember { mutableFloatStateOf(0f) }
    val offset = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(isActionsRevealed, contextMenuWidth) {
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
        modifier = modifier.border(
            width = 1.dp,
            color = getColorByPriority(task.priority),
            shape = CardDefaults.shape
        )
    ) {
        TaskCardContent(
            task = task,
            isTaskCompleted = isTaskCompleted,
            onTaskCompletionChange = { isChecked ->
                isTaskCompleted = isChecked
                onTaskCompletionChange(isChecked)
            },
            onTaskDelete = onTaskDelete,
            onPriorityClick = { showTaskPriorityDialog = true },
            offset = offset,
            contextMenuWidth = contextMenuWidth,
            onSizeChanged = { contextMenuWidth = it.width.toFloat() },
            scope = scope
        )
    }
}

@Composable
private fun TaskCardContent(
    task: Task,
    isTaskCompleted: Boolean,
    onTaskCompletionChange: (Boolean) -> Unit,
    onTaskDelete: () -> Unit,
    onPriorityClick: () -> Unit,
    offset: Animatable<Float, *>,
    contextMenuWidth: Float,
    onSizeChanged: (IntSize) -> Unit,
    scope: CoroutineScope
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(if (task.date != null) 70.dp else 60.dp)
    ) {
        TaskActions(
            onSizeChanged = onSizeChanged,
            onTaskDelete = onTaskDelete,
            onPriorityClick = onPriorityClick,
            taskPriority = task.priority
        )

        TaskContent(
            task = task,
            isTaskCompleted = isTaskCompleted,
            onTaskCompletionChange = onTaskCompletionChange,
            offset = offset,
            contextMenuWidth = contextMenuWidth,
            scope = scope
        )
    }
}

@Composable
private fun BoxScope.TaskActions(
    onSizeChanged: (IntSize) -> Unit,
    onTaskDelete: () -> Unit,
    onPriorityClick: () -> Unit,
    taskPriority: Task.Priority
) {
    val priorityColor = when (taskPriority) {
        Task.Priority.TopPriority -> Support.Warning.Dark
        Task.Priority.SecondaryPriority -> Support.Warning.Medium
        Task.Priority.NoPriority -> Neutral.Dark.Lightest
    }

    Row(
        modifier = Modifier
            .align(Alignment.CenterEnd)
            .onSizeChanged { size -> onSizeChanged(size) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        TaskActionIcon(
            imageVector = Icons.Default.Star,
            text = stringResource(R.string.priority),
            contentColor = Neutral.Light.Lightest,
            containerColor = priorityColor,
            modifier = Modifier.fillMaxHeight(),
            onClick =
            {
                onPriorityClick()
            }
        )

        TaskActionIcon(
            imageVector = Icons.Default.Delete,
            text = stringResource(R.string.delete),
            contentColor = Neutral.Light.Lightest,
            containerColor = Support.Error.Dark,
            modifier = Modifier.fillMaxHeight(),
            onClick = onTaskDelete
        )
    }
}

@Composable
private fun TaskContent(
    task: Task,
    isTaskCompleted: Boolean,
    onTaskCompletionChange: (Boolean) -> Unit,
    offset: Animatable<Float, *>,
    contextMenuWidth: Float,
    scope: CoroutineScope
) {
    val priorityColor = getColorByPriority(task.priority)

    Surface(
        color = if (isTaskCompleted) Highlight.Lightest else Neutral.Light.Lightest,
        modifier = Modifier
            .offset { IntOffset(offset.value.roundToInt(), 0) }
            .pointerInput(contextMenuWidth) {
                detectHorizontalDragGestures(
                    onHorizontalDrag = { _, dragAmount ->
                        scope.launch {
                            val newOffset =
                                (offset.value + dragAmount).coerceIn(-contextMenuWidth, 0f)
                            offset.snapTo(newOffset)
                        }
                    },
                    onDragEnd = {
                        scope.launch {
                            if (offset.value <= -contextMenuWidth / 2f) {
                                offset.animateTo(-contextMenuWidth)
                            } else {
                                offset.animateTo(0f)
                            }
                        }
                    }
                )
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(8.dp)
                    .background(priorityColor)
            )

            Checkbox(
                checked = isTaskCompleted,
                onCheckedChange = onTaskCompletionChange,
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
                        text = it,
                        fontSize = 12.sp,
                        color = Neutral.Dark.Light
                    )
                }
            }
        }
    }
}

private fun getColorByPriority(priority: Task.Priority) = when (priority) {
    Task.Priority.TopPriority -> Support.Warning.Dark
    Task.Priority.SecondaryPriority -> Support.Warning.Medium
    Task.Priority.NoPriority -> Neutral.Dark.Lightest
}


@Preview(showSystemUi = true)
@Composable
fun TaskCardPreview() {
    Column(
        modifier = Modifier
            .padding(LocalSpacing.current.small)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(
            LocalSpacing.current.small
        )
    ) {
        (1..10).forEach {
            TaskListItem(
                Task(
                    id = it.toLong(),
                    text = "Task $it",
                    isCompleted = it > 5,
                    date = if (it % 2 == 0) "10-08" else null,
                    priority = Task.Priority.fromValue(it % 3)
                ),
                onTaskCompletionChange = { _ -> },
                onTaskDelete = { },
                onPriorityChange = { _ -> },
            )
        }
    }
}