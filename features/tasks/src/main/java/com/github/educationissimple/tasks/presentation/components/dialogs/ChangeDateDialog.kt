package com.github.educationissimple.tasks.presentation.components.dialogs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.github.educationissimple.components.colors.Highlight
import com.github.educationissimple.components.composables.DefaultDialog
import com.github.educationissimple.tasks.R
import com.github.educationissimple.tasks.presentation.components.CalendarView
import java.time.LocalDate

@Composable
fun ChangeDateDialog(
    onConfirm: (LocalDate) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var date by remember { mutableStateOf(LocalDate.now()) }

    DefaultDialog(
        onDismiss = onDismiss,
        title = stringResource(R.string.select_task_date),
        modifier = modifier
    ) {
        Column(horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()) {
            CalendarView(
                selectedDate = date,
                onDaySelect = { date = it },
                modifier = Modifier.scale(0.85f)
            )

            TextButton(onClick = {
                onConfirm(date)
            }, modifier = Modifier.align(Alignment.End)) {
                Text(
                    text = stringResource(R.string.ok),
                    color = Highlight.Darkest,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ChangeDateDialogPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        ChangeDateDialog(onConfirm = { }, onDismiss = { })
    }
}