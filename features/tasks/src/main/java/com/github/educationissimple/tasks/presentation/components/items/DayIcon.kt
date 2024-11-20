package com.github.educationissimple.tasks.presentation.components.items

import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

/**
 * Displays a clickable icon button representing a day.
 *
 * This composable function creates an icon button with the day's number (provided by the [day] parameter) displayed as text.
 * When the button is clicked, it invokes the [onClick] callback. The appearance of the icon changes depending on whether the day is selected or not.
 * - If the day is selected ([selected] is `true`), the button's background is highlighted using the primary color of the theme.
 * - If the day is not selected, the button is transparent with a neutral content color.
 *
 * The button's appearance and layout can be customized with the [modifier] parameter.
 *
 * @param day The day number to display on the button.
 * @param selected A boolean indicating whether the day is selected. When `true`, the button is highlighted.
 * @param onClick A callback function invoked when the button is clicked.
 * @param modifier A [Modifier] that can be used to customize the layout or appearance of the icon button.
 */
@Composable
fun DayIcon(day: Int, selected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {

    IconButton(
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = if (selected) MaterialTheme.colorScheme.primary else Color.Transparent,
            contentColor = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
        ),
        modifier = modifier
    ) {
        Text(text = day.toString(), fontWeight = FontWeight.Bold)
    }

}

@Preview(showBackground = true)
@Composable
fun DayIconPreview() {
    DayIcon(day = 13, selected = true, onClick = {})
}