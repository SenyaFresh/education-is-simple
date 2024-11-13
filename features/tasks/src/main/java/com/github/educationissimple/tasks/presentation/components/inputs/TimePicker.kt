package com.github.educationissimple.tasks.presentation.components.inputs

import androidx.compose.foundation.Canvas
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

    LaunchedEffect(selectedHour, selectedMinute) {
        onTimeSelect(LocalTime.of(selectedHour, selectedMinute))
    }

    Row(
        modifier = Modifier.height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            OutlinedTextField(
                value = selectedHour.toString(),
                onValueChange = { if (it.toInt() in 0..23) selectedHour = it.toInt() },
                modifier = Modifier.size(width = 100.dp, height = 80.dp),
                textStyle = TextStyle.Default.copy(fontSize = 48.sp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Neutral.Light.Lightest,
                    unfocusedContainerColor = Neutral.Light.Medium,
                    focusedBorderColor = Highlight.Darkest,
                    unfocusedBorderColor = Color.Transparent
                )
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
                value = if (selectedMinute < 10) "0$selectedMinute" else selectedMinute.toString(),
                onValueChange = { if (it.toInt() in 0..59) selectedMinute = it.toInt() },
                modifier = Modifier.size(width = 100.dp, height = 80.dp),
                textStyle = TextStyle.Default.copy(fontSize = 48.sp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Neutral.Light.Lightest,
                    unfocusedContainerColor = Neutral.Light.Medium,
                    focusedBorderColor = Highlight.Darkest,
                    unfocusedBorderColor = Color.Transparent
                )
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