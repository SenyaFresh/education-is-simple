package com.github.educationissimple.components.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.github.educationissimple.components.colors.Highlight
import com.github.educationissimple.components.colors.Neutral

@Composable
fun DefaultSecondaryButton(label: String, onClick: () -> Unit, modifier: Modifier = Modifier) =
    OutlinedButton (
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Neutral.Light.Lightest,
            contentColor = Highlight.Darkest,
        ),
        border = BorderStroke(width = 2.dp, color = Highlight.Darkest),
        modifier = modifier
    ) {
        Text(text = label, fontWeight = FontWeight.SemiBold)
    }