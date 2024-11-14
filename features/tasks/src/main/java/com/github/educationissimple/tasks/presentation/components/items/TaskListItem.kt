package com.github.educationissimple.tasks.presentation.components.items

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
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
import androidx.compose.ui.draw.clip
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
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.components.colors.Highlight
import com.github.educationissimple.components.colors.Neutral
import com.github.educationissimple.components.colors.Support
import com.github.educationissimple.components.composables.ActionIcon
import com.github.educationissimple.components.composables.shimmerEffect
import com.github.educationissimple.presentation.locals.LocalSpacing
import com.github.educationissimple.tasks.R
import com.github.educationissimple.tasks.domain.entities.Task
import com.github.educationissimple.tasks.domain.entities.TaskCategory
import com.github.educationissimple.tasks.domain.entities.TaskReminder
import com.github.educationissimple.tasks.domain.utils.toTaskDate
import com.github.educationissimple.tasks.presentation.components.dialogs.ChangeDateDialog
import com.github.educationissimple.tasks.presentation.components.dialogs.TaskPriorityDialog
import com.github.educationissimple.tasks.presentation.components.environment.TaskSheet
import com.github.educationissimple.tasks.presentation.utils.toColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.math.roundToInt

@Composable
fun TaskListItem(
    task: Task,
    categories: ResultContainer<List<TaskCategory>>,
    reminders: ResultContainer<List<TaskReminder>>,
    onAddNewCategory: (String) -> Unit,
    onTaskDelete: () -> Unit,
    onTaskUpdate: (Task) -> Unit,
    onCreateReminder: (TaskReminder) -> Unit,
    onDeleteReminder: (TaskReminder) -> Unit,
    modifier: Modifier = Modifier,
    isActionsRevealed: Boolean = false
) {
    var isTaskCompleted by remember { mutableStateOf(task.isCompleted) }
    var showTaskPriorityDialog by remember { mutableStateOf(false) }
    var showTaskDateDialog by remember { mutableStateOf(false) }
    var showTaskPropertiesSheet by remember { mutableStateOf(false) }
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
            onPriorityChange = { onTaskUpdate(task.copy(priority = it)) },
            priority = task.priority
        )
    }

    if (showTaskDateDialog) {
        ChangeDateDialog(
            onDismiss = { showTaskDateDialog = false },
            onConfirm = {
                onTaskUpdate(task.copy(date = it))
                showTaskDateDialog = false
            },
            initialDate = task.date ?: LocalDate.now()
        )
    }

    if (showTaskPropertiesSheet) {
        TaskSheet(
            task = task,
            categories = categories,
            reminders = reminders,
            onCreateReminder = onCreateReminder,
            onDeleteReminder = onDeleteReminder,
            onAddNewCategory = onAddNewCategory,
            isSheetOpen = showTaskPropertiesSheet,
            onTaskUpdate = onTaskUpdate,
            onDismiss = { showTaskPropertiesSheet = false }
        )
    }

    Card(
        modifier = modifier.border(
            width = 1.dp,
            color = task.priority.toColor(),
            shape = CardDefaults.shape
        ),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        TaskCardContent(
            task = task,
            isTaskCompleted = isTaskCompleted,
            onTaskCompletionChange = { isChecked ->
                isTaskCompleted = isChecked
                onTaskUpdate(task.copy(isCompleted = isChecked))
            },
            onTaskDelete = onTaskDelete,
            onTaskClicked = { showTaskPropertiesSheet = true },
            onPriorityClick = { showTaskPriorityDialog = true },
            onDateClick = { showTaskDateDialog = true },
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
    onTaskClicked: () -> Unit,
    onPriorityClick: () -> Unit,
    onDateClick: () -> Unit,
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
            onDateClick = onDateClick,
            taskPriority = task.priority
        )

        TaskContent(
            task = task,
            isTaskCompleted = isTaskCompleted,
            onTaskCompletionChange = onTaskCompletionChange,
            offset = offset,
            contextMenuWidth = contextMenuWidth,
            scope = scope,
            modifier = Modifier.clickable { onTaskClicked() }
        )
    }
}

@Composable
private fun BoxScope.TaskActions(
    onSizeChanged: (IntSize) -> Unit,
    onTaskDelete: () -> Unit,
    onPriorityClick: () -> Unit,
    onDateClick: () -> Unit,
    taskPriority: Task.Priority,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .align(Alignment.CenterEnd)
            .onSizeChanged { size -> onSizeChanged(size) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        ActionIcon(
            imageVector = Icons.Default.DateRange,
            text = stringResource(R.string.date),
            contentColor = Neutral.Light.Lightest,
            containerColor = Highlight.Darkest,
            modifier = Modifier.fillMaxHeight(),
            onClick = onDateClick
        )

        ActionIcon(
            imageVector = Icons.Default.Star,
            text = stringResource(R.string.priority),
            contentColor = Neutral.Light.Lightest,
            containerColor = taskPriority.toColor(),
            modifier = Modifier.fillMaxHeight(),
            onClick =
            {
                onPriorityClick()
            }
        )

        ActionIcon(
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
    scope: CoroutineScope,
    modifier: Modifier = Modifier
) {
    val priorityColor = task.priority.toColor()

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
            modifier = modifier.fillMaxSize()
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(LocalSpacing.current.small)
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
                task.date?.toTaskDate()?.let {
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

@Composable
fun LoadingTaskListItem() {
    Row (
        modifier = Modifier
            .clip(shape = CardDefaults.shape)
            .fillMaxWidth()
            .height(60.dp)
            .shimmerEffect(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(
            modifier = Modifier.width(LocalSpacing.current.medium)
        )

        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(4.dp))
                .size(20.dp)
                .background(color = Neutral.Dark.Light)
        )

        Spacer(
            modifier = Modifier.width(LocalSpacing.current.medium)
        )

        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(4.dp))
                .size(height = 14.dp, width = (72..120).random().dp)
                .background(color = Neutral.Dark.Light)
        )
    }
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
                    date = if (it % 2 == 0) LocalDate.now() else null,
                    priority = Task.Priority.fromValue(it % 3)
                ),
                categories = ResultContainer.Done(emptyList()),
                reminders = ResultContainer.Done(emptyList()),
                onTaskDelete = {},
                onTaskUpdate = {},
                onAddNewCategory = {},
                onCreateReminder = {},
                onDeleteReminder = {}
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun LoadingTaskCardPreview() {
    Column(
        modifier = Modifier
            .padding(LocalSpacing.current.medium)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(
            LocalSpacing.current.small
        )
    ) {
        repeat(10) {
            LoadingTaskListItem()
        }
    }
}