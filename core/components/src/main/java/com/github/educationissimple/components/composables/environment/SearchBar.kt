package com.github.educationissimple.components.composables.environment

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.github.educationissimple.components.colors.Neutral

@Composable
fun SearchBar(
    text: String,
    onValueChange: (String) -> Unit,
    onCancelClick: () -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {

    TextField(
        value = text,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                modifier = Modifier.clickable {
                    onCancelClick()
                }
            )
        },
        label = { Text(label) },
        shape = RoundedCornerShape(100),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Neutral.Light.Light,
            focusedContainerColor = Neutral.Light.Light,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        modifier = modifier.fillMaxWidth()
    )
}

@Preview
@Composable
fun SearchBarPreview() {
    SearchBar(text = "", onValueChange = {}, onCancelClick = {}, label = "Поиск")
}