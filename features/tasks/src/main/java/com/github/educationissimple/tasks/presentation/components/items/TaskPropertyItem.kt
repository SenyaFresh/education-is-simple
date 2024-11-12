package com.github.educationissimple.tasks.presentation.components.items

import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.educationissimple.components.colors.Neutral
import com.github.educationissimple.presentation.locals.LocalSpacing

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
            tint = Neutral.Dark.Dark,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = label,
            color = Neutral.Dark.Medium,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = if (rightArrowOpened) Icons.AutoMirrored.Filled.ArrowBackIos else Icons.AutoMirrored.Filled.ArrowForwardIos,
            contentDescription = null,
            tint = Neutral.Dark.Lightest,
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
        modifier = Modifier.background(color = Neutral.Light.Lightest),
        rightArrowOpened = false,
        onPropertyClick = {}
    )
}