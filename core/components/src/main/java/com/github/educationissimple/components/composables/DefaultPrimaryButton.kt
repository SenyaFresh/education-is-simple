package com.github.educationissimple.components.composables

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.github.educationissimple.components.colors.Highlight
import com.github.educationissimple.components.colors.Neutral

@Composable
fun DefaultPrimaryButton(label: String, onClick: () -> Unit, modifier: Modifier = Modifier) =
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Highlight.Darkest,
            contentColor = Neutral.Light.Lightest
        ),
        modifier = modifier
    ) {
        Text(text = label, fontWeight = FontWeight.SemiBold)
    }
