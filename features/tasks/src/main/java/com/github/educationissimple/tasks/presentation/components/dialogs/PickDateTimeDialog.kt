package com.github.educationissimple.tasks.presentation.components.dialogs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.github.educationissimple.components.composables.dialogs.DefaultDialog
import com.github.educationissimple.tasks.R
import com.github.educationissimple.tasks.presentation.components.inputs.CalendarView
import com.github.educationissimple.tasks.presentation.components.inputs.TimePicker
import java.time.LocalDateTime

@Composable
fun PickDateTimeDialog(
    onConfirm: (LocalDateTime) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    initialDateTime: LocalDateTime = LocalDateTime.now()
) {
    var date by remember { mutableStateOf(initialDateTime.toLocalDate()) }
    var time by remember { mutableStateOf(initialDateTime.toLocalTime()) }

    var datePicked by remember { mutableStateOf(false) }

    DefaultDialog(
        onDismiss = onDismiss,
        title = if (!datePicked) stringResource(R.string.select_reminder_date)
                else stringResource(R.string.select_reminder_time),
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (!datePicked) {
                CalendarView(
                    selectedDate = date,
                    onDaySelect = { date = it },
                    modifier = Modifier.scale(0.85f)
                )

                TextButton(onClick = {
                    datePicked = true
                }, modifier = Modifier.align(Alignment.End)) {
                    Text(
                        text = stringResource(R.string.continue_),
                        fontSize = 16.sp
                    )
                }
            } else {
                TimePicker(
                    selectedTime = time,
                    onTimeSelect = { time = it },
                    modifier = Modifier.scale(0.85f)
                )

                Row {
                    TextButton(onClick = {
                        datePicked = false
                    }) {
                        Text(
                            text = stringResource(R.string.go_back),
                            fontSize = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    TextButton(onClick = {
                        onConfirm(date.atTime(time))
                    }) {
                        Text(
                            text = stringResource(R.string.ok),
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PickDateTimeDialogPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        PickDateTimeDialog(onConfirm = { }, onDismiss = { })
    }
}