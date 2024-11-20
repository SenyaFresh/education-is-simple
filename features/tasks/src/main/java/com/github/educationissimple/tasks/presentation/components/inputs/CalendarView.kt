package com.github.educationissimple.tasks.presentation.components.inputs

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.educationissimple.components.composables.items.ActionableListItem
import com.github.educationissimple.presentation.locals.LocalSpacing
import com.github.educationissimple.tasks.R
import com.github.educationissimple.tasks.presentation.components.items.DayIcon
import com.github.educationissimple.tasks.presentation.utils.monthToString
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Month

/**
 * Displays a calendar view with month, year, and day selection capabilities.
 *
 * This composable function renders a calendar interface where the user can select a specific
 * day, month, or year. The calendar allows for dynamic navigation through months and years,
 * as well as selecting specific days. The calendar’s state transitions between day, month, and
 * year selection modes based on user interaction.
 *
 * The calendar view is initially populated with the selected date and updates the [selectedDate]
 * whenever the user selects a new date. The current selection is communicated to the parent
 * through the [onDaySelect] callback.
 *
 * The view allows the user to:
 * - Navigate between months using left and right arrow buttons.
 * - Select a month or year to change the calendar’s display.
 * - Select a day and update the selected date.
 *
 * The appearance of the calendar view can be customized with the [modifier] parameter.
 *
 * @param modifier A [Modifier] that can be used to customize the layout or appearance of the calendar.
 * @param selectedDate The currently selected date to display in the calendar. Defaults to the current date.
 * @param onDaySelect A callback function to be invoked when the user selects a new day. It takes
 * the selected [LocalDate] as a parameter.
 */
@Composable
fun CalendarView(
    modifier: Modifier = Modifier,
    selectedDate: LocalDate = LocalDate.now(),
    onDaySelect: (LocalDate) -> Unit = { }
) {
    var calendarState by remember { mutableStateOf(CalendarState.DAY_SELECTION) }
    var selectedDay by remember { mutableStateOf(selectedDate) }

    LaunchedEffect(selectedDay) {
        onDaySelect(selectedDay)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.requiredWidthIn(min = (48 * 7).dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(enabled = calendarState == CalendarState.DAY_SELECTION, onClick = {
                selectedDay = selectedDay.minusMonths(1).withDayOfMonth(1)
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = null
                )
            }

            Row(modifier = Modifier.clickable {
                calendarState =
                    if (calendarState != CalendarState.MONTH_SELECTION) CalendarState.MONTH_SELECTION
                    else CalendarState.DAY_SELECTION
            }) {
                Text(text = monthToString(selectedDay.month), fontWeight = FontWeight.Bold)
                Icon(
                    if (calendarState == CalendarState.MONTH_SELECTION) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = if (calendarState == CalendarState.MONTH_SELECTION) "Collapse" else "Expand",
                    modifier = Modifier.size(16.dp)
                )
            }

            Row(modifier = Modifier.clickable {
                calendarState =
                    if (calendarState != CalendarState.YEAR_SELECTION) CalendarState.YEAR_SELECTION
                    else CalendarState.DAY_SELECTION
            }) {
                Text(text = selectedDay.year.toString(), fontWeight = FontWeight.Bold)
                Icon(
                    if (calendarState == CalendarState.YEAR_SELECTION) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = if (calendarState == CalendarState.YEAR_SELECTION) "Collapse" else "Expand",
                    modifier = Modifier.size(16.dp)
                )
            }

            IconButton(enabled = calendarState == CalendarState.DAY_SELECTION, onClick = {
                selectedDay = selectedDay.plusMonths(1).withDayOfMonth(1)
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null
                )
            }
        }
        HorizontalDivider()
        Crossfade(
            targetState = calendarState,
            label = "CalendarState",
            modifier = Modifier.animateContentSize()
        ) { state ->
            when (state) {
                CalendarState.DAY_SELECTION -> {}

                CalendarState.MONTH_SELECTION -> {
                    CalendarMonthSelection(
                        selectedMonth = selectedDay.month,
                        onMonthSelect = { month ->
                            selectedDay = selectedDay.withMonth(month.value)
                        }
                    )
                }

                CalendarState.YEAR_SELECTION -> {
                    CalendarYearSelection(
                        selectedYear = selectedDay.year,
                        onYearSelect = { year ->
                            selectedDay = selectedDay.withYear(year)
                        }
                    )
                }
            }
        }

        HorizontalDivider()
        Spacer(modifier = Modifier.height(LocalSpacing.current.medium))
        CalendarDaySelection(date = selectedDay,
            onDaySelect = { date -> selectedDay = date })
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CalendarDaySelection(date: LocalDate, onDaySelect: (LocalDate) -> Unit) {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            listOf(
                R.string.monday_2_symbols,
                R.string.tuesday_2_symbols,
                R.string.wednesday_2_symbols,
                R.string.thursday_2_symbols,
                R.string.friday_2_symbols,
                R.string.saturday_2_symbols,
                R.string.sunday_2_symbols
            ).forEach { weekDayResId ->
                Text(
                    text = stringResource(weekDayResId),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.width(48.dp)
                )
            }
        }
        FlowRow(
            horizontalArrangement = Arrangement.SpaceBetween,
            maxItemsInEachRow = 7,
            modifier = Modifier.fillMaxWidth()
        ) {
            val monthStartDay = date.withDayOfMonth(1)
            val daysInMonth = date.lengthOfMonth()

            val leadingEmptyBoxes = monthStartDay.dayOfWeek.value - 1
            val trailingEmptyBoxes = (7 - ((daysInMonth + leadingEmptyBoxes) % 7))

            repeat(leadingEmptyBoxes) {
                Box(modifier = Modifier.size(48.dp))
            }

            (1..daysInMonth).forEach { dayOfMonth ->
                DayIcon(day = dayOfMonth, selected = dayOfMonth == date.dayOfMonth, onClick = {
                    onDaySelect(date.withDayOfMonth(dayOfMonth))
                })
            }

            repeat(trailingEmptyBoxes) {
                Box(modifier = Modifier.size(48.dp))
            }
        }
    }
}

@Composable
fun CalendarMonthSelection(
    selectedMonth: Month, onMonthSelect: (Month) -> Unit, modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val pagerState =
        rememberPagerState(initialPage = (Int.MAX_VALUE / 2 - Int.MAX_VALUE / 2 % 12) + selectedMonth.ordinal,
            pageCount = { Int.MAX_VALUE })
    var currentPage by remember { mutableIntStateOf(pagerState.currentPage) }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            currentPage = page
            onMonthSelect(Month.entries[page % 12])
        }
    }

    HorizontalPager(
        state = pagerState,
        pageSize = PageSize.Fixed(120.dp),
        snapPosition = SnapPosition.Center,
        key = { page -> Pair(page, selectedMonth) },
        modifier = modifier
    ) { page ->
        Box(modifier = Modifier.fillMaxWidth()) {
            ActionableListItem(
                label = monthToString(Month.entries[page % 12]),
                onClick = { scope.launch { pagerState.scrollToPage(page) } },
                modifier = Modifier
                    .align(Alignment.Center)
                    .scale(0.9f),
                isActive = page == currentPage
            )
        }
    }
}

@Composable
fun CalendarYearSelection(
    selectedYear: Int, onYearSelect: (Int) -> Unit, modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(initialPage = selectedYear, pageCount = { Int.MAX_VALUE })
    var currentPage by remember { mutableIntStateOf(pagerState.currentPage) }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            currentPage = page
            onYearSelect(page)
        }
    }

    HorizontalPager(
        state = pagerState,
        pageSize = PageSize.Fixed(80.dp),
        snapPosition = SnapPosition.Center,
        key = { page -> Pair(page, selectedYear) },
        modifier = modifier
    ) { page ->
        Box(modifier = Modifier.fillMaxWidth()) {
            ActionableListItem(
                label = page.toString(),
                onClick = { scope.launch { pagerState.scrollToPage(page) } },
                modifier = Modifier
                    .align(Alignment.Center)
                    .scale(0.9f),
                isActive = page == currentPage
            )
        }
    }

}


enum class CalendarState {
    DAY_SELECTION, MONTH_SELECTION, YEAR_SELECTION
}

@Preview(showBackground = true)
@Composable
fun CalendarViewPreview() {
    CalendarView()
}