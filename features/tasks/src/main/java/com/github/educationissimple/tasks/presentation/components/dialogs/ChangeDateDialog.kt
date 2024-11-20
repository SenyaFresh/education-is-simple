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
import com.github.educationissimple.components.composables.dialogs.DefaultDialog
import com.github.educationissimple.tasks.R
import com.github.educationissimple.tasks.presentation.components.inputs.CalendarView
import java.time.LocalDate

/**
 * Displays a dialog for selecting a date.
 *
 * This composable function presents a dialog that allows the user to select a date using a calendar view.
 * - The user can pick a date by selecting a day on the calendar.
 * - When the user confirms their selection, the provided [onConfirm] callback is triggered with the selected date.
 * - When the dialog is dismissed, the [onDismiss] callback is triggered.
 *
 * The dialog's appearance can be customized using the [modifier] parameter. By default, the dialog
 * opens with the current date as the initial selection.
 *
 * @param onConfirm A callback function to be invoked when the user confirms their date selection. It takes
 * the selected [LocalDate] as a parameter.
 * @param onDismiss A callback function to be invoked when the user dismisses the dialog (e.g., pressing "back").
 * @param modifier A [Modifier] that can be used to customize the layout or appearance of the dialog.
 * @param initialDate The initial date shown on the calendar when the dialog opens. Defaults to the current date.
 */
@Composable
fun ChangeDateDialog(
    onConfirm: (LocalDate) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    initialDate: LocalDate = LocalDate.now()
) {
    var date by remember { mutableStateOf(initialDate) }

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