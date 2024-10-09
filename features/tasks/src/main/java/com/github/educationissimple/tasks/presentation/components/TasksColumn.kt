package com.github.educationissimple.tasks.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.github.educationissimple.tasks.domain.entities.Task


@Composable
fun TasksColumn(title: String, tasks: List<Task>) {

    var isListVisible by rememberSaveable { mutableStateOf(false) }

    Column {
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(text = title)
            IconButton(onClick = { isListVisible = !isListVisible }) {
                if (isListVisible) {
                    Icons.Filled.KeyboardArrowDown
                } else {
                    Icons.Filled.KeyboardArrowUp
                }
            }
        }

        LazyColumn(contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp)) {

        }
    }

}