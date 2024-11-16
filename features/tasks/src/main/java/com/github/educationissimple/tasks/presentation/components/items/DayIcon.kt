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