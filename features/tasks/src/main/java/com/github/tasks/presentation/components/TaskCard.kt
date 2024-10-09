package com.github.tasks.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.components.colors.Highlight
import com.github.components.colors.Neutral

@Composable
fun TaskCard(isCompleted: Boolean, text: String, date: String? = null) {

    var checkBoxFlag by rememberSaveable { mutableStateOf(isCompleted) }

    OutlinedCard(
        colors = CardDefaults.cardColors(containerColor = if (checkBoxFlag) Highlight.Lightest else Neutral.Light.Lightest),
        border = CardDefaults.outlinedCardBorder(enabled = !isCompleted),
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 12.dp)
        ) {
            Checkbox(
                checked = checkBoxFlag,
                onCheckedChange = { checkBoxFlag = it },
                colors = CheckboxDefaults.colors(
                    uncheckedColor = Neutral.Light.Darkest,
                    checkedColor = Highlight.Darkest
                ),
                modifier = Modifier.scale(0.9f)
            )

            Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                Text(
                    text = text,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(textDecoration = if (isCompleted) TextDecoration.LineThrough else null),
                )

                if (date != null) {
                    Text(
                        text = date,
                        fontSize = 12.sp,
                        color = Neutral.Dark.Light
                    )
                }
            }

        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun TaskCardPreview() {
    Column(modifier = Modifier.padding(4.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
        TaskCard(false, "Go to work", "10-08")

        TaskCard(true, "Task with long long long long long long long long long long long long")
    }
}