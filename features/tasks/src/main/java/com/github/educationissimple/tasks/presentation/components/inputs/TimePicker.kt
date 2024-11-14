package com.github.educationissimple.tasks.presentation.components.inputs

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.educationissimple.components.colors.Highlight
import com.github.educationissimple.components.colors.Neutral
import java.time.LocalTime

@Composable
fun TimePicker(
    modifier: Modifier = Modifier,
    selectedTime: LocalTime = LocalTime.now(),
    onTimeSelect: (LocalTime) -> Unit = { }
) {
    var selectedHour by remember { mutableIntStateOf(selectedTime.hour) }
    var selectedMinute by remember { mutableIntStateOf(selectedTime.minute) }

    val hoursInteractionSource = remember { MutableInteractionSource() }
    val isHoursFocused by hoursInteractionSource.collectIsFocusedAsState()

    val minutesInteractionSource = remember { MutableInteractionSource() }
    val isMinutesFocused by minutesInteractionSource.collectIsFocusedAsState()

    LaunchedEffect(selectedHour, selectedMinute) {
        onTimeSelect(LocalTime.of(selectedHour, selectedMinute))
    }

    Row(
        modifier = modifier.height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            OutlinedTextField(
                value = formatTime(selectedHour, isHoursFocused),
                onValueChange = { parseInput(it, 0..23)?.let { hour -> selectedHour = hour } },
                modifier = Modifier.size(width = 100.dp, height = 80.dp),
                textStyle = TextStyle.Default.copy(fontSize = 48.sp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Neutral.Light.Lightest,
                    unfocusedContainerColor = Neutral.Light.Medium,
                    focusedBorderColor = Highlight.Darkest,
                    unfocusedBorderColor = Color.Transparent
                ),
                interactionSource = hoursInteractionSource
            )
            Text(text = "Часы", color = Neutral.Dark.Dark)
        }

        Column(
            modifier = Modifier.fillMaxHeight().padding(start = 8.dp, end = 8.dp, bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Canvas(modifier = Modifier.size(8.dp), onDraw = {
                drawCircle(color = Neutral.Dark.Dark)
            })
            Canvas(modifier = Modifier.size(8.dp), onDraw = {
                drawCircle(color = Neutral.Dark.Dark)
            })
        }

        Column(
            horizontalAlignment = Alignment.Start
        ) {
            OutlinedTextField(
                value = formatTime(selectedMinute, isMinutesFocused),
                onValueChange = { parseInput(it, 0..59)?.let { minute -> selectedMinute = minute } },
                modifier = Modifier.size(width = 100.dp, height = 80.dp),
                textStyle = TextStyle.Default.copy(fontSize = 48.sp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Neutral.Light.Lightest,
                    unfocusedContainerColor = Neutral.Light.Medium,
                    focusedBorderColor = Highlight.Darkest,
                    unfocusedBorderColor = Color.Transparent
                ),
                interactionSource = minutesInteractionSource
            )
            Text(text = "Минуты", color = Neutral.Dark.Dark)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun TimePickerPreview() {
    TimePicker()
}

private fun parseInput(input: String, range: IntRange): Int? {
    if (input.isEmpty()) return 0
    val number = input.toIntOrNull()
    return if (number in range) number else null
}

private fun formatTime(time: Int, selected: Boolean): String {
    return if (selected) {
        if (time == 0) "" else time.toString()
    } else {
        if (time < 10) "0$time" else time.toString()
    }
}