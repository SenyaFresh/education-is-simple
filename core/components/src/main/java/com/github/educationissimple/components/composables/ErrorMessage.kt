package com.github.educationissimple.components.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.github.educationissimple.common.Core
import com.github.educationissimple.components.R
import com.github.educationissimple.components.colors.Neutral
import com.github.educationissimple.components.colors.Support
import com.github.educationissimple.components.composables.buttons.DefaultPrimaryButton

@Composable
fun ErrorMessage(
    message: String,
    onClickRetry: () -> Unit
) {
    Column {
        Text(text = message)
        DefaultPrimaryButton(
            label = Core.resources.getString(R.string.retry),
            onClick = onClickRetry,
            colors = ButtonDefaults.buttonColors(
                containerColor = Support.Error.Dark,
                contentColor = Neutral.Light.Lightest
            )
        )
    }
}