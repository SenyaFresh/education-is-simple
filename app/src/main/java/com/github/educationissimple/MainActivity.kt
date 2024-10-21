package com.github.educationissimple

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.github.educationissimple.tasks.presentation.screens.TasksScreen
import com.github.educationissimple.ui.theme.EducationIsSimpleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EducationIsSimpleTheme {
                TasksScreen()
            }
        }
    }
}