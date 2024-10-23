package com.github.educationissimple.tasks.presentation.components.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.educationissimple.components.colors.Highlight
import com.github.educationissimple.components.colors.Neutral
import com.github.educationissimple.tasks.R
import com.github.educationissimple.tasks.domain.entities.SortType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksSortDialog(
    onDismiss: () -> Unit,
    onSortTypeChange: (SortType) -> Unit,
    sortType: SortType?,
    modifier: Modifier = Modifier
) {
    BasicAlertDialog(
        onDismissRequest = onDismiss,
        modifier = modifier,
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            tonalElevation = AlertDialogDefaults.TonalElevation,
            shape = RoundedCornerShape(12.dp),
            color = Neutral.Light.Lightest
        ) {
            Column(horizontalAlignment = Alignment.Start, modifier = Modifier.padding(12.dp)) {
                Text(
                    text = stringResource(R.string.select_sort_type),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(14.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onSortTypeChange(SortType.Priority) }
                ) {
                    RadioButton(
                        selected = sortType is SortType.Priority,
                        colors = RadioButtonDefaults.colors(
                            unselectedColor = Neutral.Dark.Darkest,
                            selectedColor = Highlight.Darkest
                        ),
                        onClick = { onSortTypeChange(SortType.Priority) }
                    )
                    Text(text = stringResource(R.string.by_priority), color = Neutral.Dark.Darkest)
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onSortTypeChange(SortType.Date) }
                ) {
                    RadioButton(
                        selected = sortType is SortType.Date,
                        colors = RadioButtonDefaults.colors(
                            unselectedColor = Neutral.Dark.Darkest,
                            selectedColor = Highlight.Darkest
                        ),
                        onClick = { onSortTypeChange(SortType.Date) }
                    )
                    Text(text = stringResource(R.string.by_date), color = Neutral.Dark.Darkest)
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onSortTypeChange(SortType.Text) }
                ) {
                    RadioButton(
                        selected = sortType is SortType.Text,
                        colors = RadioButtonDefaults.colors(
                            unselectedColor = Neutral.Dark.Darkest,
                            selectedColor = Highlight.Darkest
                        ),
                        onClick = { onSortTypeChange(SortType.Text) }
                    )
                    Text(text = stringResource(R.string.by_abc), color = Neutral.Dark.Darkest)
                }

                TextButton(onClick = onDismiss, modifier = Modifier.align(Alignment.End)) {
                    Text(
                        text = stringResource(R.string.ok),
                        color = Highlight.Darkest,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun TasksSortDialogPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        TasksSortDialog(
            onDismiss = { },
            onSortTypeChange = { },
            sortType = SortType.Priority,
            modifier = Modifier
        )
    }
}