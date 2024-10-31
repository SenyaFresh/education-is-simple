package com.github.educationissimple.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph
import androidx.navigation.compose.currentBackStackEntryAsState
import com.github.educationissimple.components.colors.Highlight
import com.github.educationissimple.components.colors.Neutral
import kotlinx.collections.immutable.ImmutableList

@Composable
fun AppNavigationBar(
    navigationController: NavController,
    tabs: ImmutableList<AppTab>
) {
    NavigationBar(
        containerColor = Neutral.Light.Lightest,
    ) {
        val currentBackStackEntry = navigationController.currentBackStackEntryAsState()
        val closestNavGraph = currentBackStackEntry
            .value
            ?.destination
            ?.hierarchy
            ?.first { it is NavGraph }
            .routeClass()

        val currentTab = tabs.firstOrNull { it.graphRoute::class == closestNavGraph }

        tabs.forEach { tab ->
            NavigationBarItem(
                selected = currentTab == tab,
                onClick = {
                    if (currentTab != null) {
                        navigationController.navigate(tab.graphRoute) {
                            popUpTo(currentTab.graphRoute) {
                                inclusive = true
                                saveState = true
                            }
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = tab.imageVector,
                        contentDescription = stringResource(id = tab.titleRes),
                    )
                },
                label = {
                    Text(text = stringResource(id = tab.titleRes))
                },
                colors = NavigationBarItemDefaults.colors(
                    unselectedTextColor = Neutral.Dark.Light,
                    unselectedIconColor = Neutral.Light.Dark,
                    selectedTextColor = Neutral.Dark.Darkest,
                    selectedIconColor = Highlight.Dark,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}