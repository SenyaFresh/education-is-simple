package com.github.educationissimple.tasks.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import com.github.educationissimple.components.colors.Neutral
import com.github.educationissimple.tasks.domain.entities.Task
import com.github.educationissimple.tasks.presentation.events.TasksEvent

@Composable
fun PopUpTextField(
    text: String,
    onValueChange: (String) -> Unit,
    onAddClick: () -> Unit,
    focusRequester: FocusRequester,
    modifier: Modifier = Modifier,
) = Box(Modifier.fillMaxSize()) {
    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Neutral.Light.Lightest,
            focusedBorderColor = Neutral.Light.Darkest,
            unfocusedContainerColor = Neutral.Light.Lightest,
            unfocusedBorderColor = Neutral.Light.Darkest,
        ),
        modifier = modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .imePadding()
            .focusRequester(focusRequester),
        trailingIcon = {
            IconButton(
                onClick = onAddClick,
                colors = IconButtonDefaults.iconButtonColors(contentColor = Neutral.Dark.Lightest)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = null
                )
            }
        }
    )
}