package com.github.educationissimple.tasks.presentation.components.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.educationissimple.presentation.locals.LocalSpacing

/**
 * Displays a clickable item for a task property with an icon, label, and an optional arrow indicating a navigable action.
 *
 * This composable function creates a row item that displays an icon, a label, and an optional arrow that changes direction depending on the value of [rightArrowOpened].
 * The entire row is clickable, triggering the [onPropertyClick] callback when clicked. This is useful for navigating to a property or editing a task's details.
 *
 * - The [iconVector] represents the icon to be displayed alongside the label. It can be any [ImageVector], such as an edit or category icon.
 * - The [label] is the text shown next to the icon, providing information about the task property.
 * - The [rightArrowOpened] parameter determines the direction of the arrow: if `true`, the arrow points backward; otherwise, it points forward.
 * - The appearance of the item is customizable through the [modifier] parameter, such as adjusting padding, size, or alignment.
 *
 * The component is visually styled with the colors from the current theme and includes a [Spacer] to ensure that the arrow is aligned at the far right of the row.
 *
 * @param iconVector An [ImageVector] that represents the icon displayed next to the label. This can be an icon representing the task property.
 * @param label A [String] representing the label or description of the task property, which will be shown next to the icon.
 * @param onPropertyClick A callback function that gets triggered when the item is clicked. It typically leads to some action or navigation, such as editing the task.
 * @param modifier A [Modifier] to customize the layout and appearance of the task property item, such as adjusting padding or size.
 * @param rightArrowOpened A boolean flag indicating whether the right arrow should point backward (`true`) or forward (`false`).
 */
@Composable
fun TaskPropertyItem(
    iconVector: ImageVector,
    label: String,
    onPropertyClick: () -> Unit,
    modifier: Modifier = Modifier,
    rightArrowOpened: Boolean = false
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(LocalSpacing.current.medium),
        modifier = modifier
            .fillMaxWidth()
            .clickable { onPropertyClick() }
            .padding(LocalSpacing.current.medium)

    ) {
        Icon(
            iconVector,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onTertiary,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onTertiary,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = if (rightArrowOpened) Icons.AutoMirrored.Filled.ArrowBackIos else Icons.AutoMirrored.Filled.ArrowForwardIos,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onTertiary,
            modifier = Modifier.size(20.dp)
        )
    }

}

@Preview
@Composable
fun TaskPropertyItemPreview() {
    TaskPropertyItem(
        iconVector = Icons.AutoMirrored.Filled.StarHalf,
        label = "Приоритет",
        rightArrowOpened = false,
        onPropertyClick = {}
    )
}