package com.github.educationissimple.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import kotlinx.serialization.Serializable
import kotlin.reflect.KClass

/**
 * Represents the navigation graph for tasks-related screens.
 **/
@Serializable
data object TasksGraph {

    @Serializable
    data object TasksScreen

    @Serializable
    data object TaskCategoriesScreen

    @Serializable
    data object CalendarScreen

    @Serializable
    data object RemindersScreen

}

/**
 * Represents the navigation graph for audio-related screens.
 **/
@Serializable
data object AudioGraph {

    @Serializable
    data object AudioScreen

    @Serializable
    data object AudioCategoriesScreen

}

/**
 * Represents the navigation graph for news-related screens.
 **/
@Serializable
data object NewsGraph {

    @Serializable
    data object NewsScreen

}

/**
 * Used to retrieve the class of the destination in a navigation back stack entry.
 *
 * @return The [KClass] of the destination, or `null` if not available.
 */
fun NavBackStackEntry?.routeClass(): KClass<*>? {
    return this
        ?.destination
        ?.routeClass()
}

/**
 * Used to get the class type of the route defined in the navigation destination.
 * The route string is expected to follow the format "className/...", and it extracts
 * the class name from the first part of the route string.
 *
 * @return The [KClass] of the route, or `null` if not found.
 */
fun NavDestination?.routeClass(): KClass<*>? {
    return this
        ?.route
        ?.split("/")
        ?.first()
        ?.let { className ->
            generateSequence(className, ::replaceLastDotByDollar)
                .mapNotNull(::tryParseClass)
                .firstOrNull()
        }
}

/**
 * Tries to parse a string into a class by its name.
 *
 * @param className The fully-qualified class name to parse.
 * @return The [KClass] of the class, or `null` if parsing fails.
 */
private fun tryParseClass(className: String): KClass<*>? {
    return runCatching { Class.forName(className).kotlin }.getOrNull()
}

private fun replaceLastDotByDollar(input: String): String? {
    val lastDotIndex = input.lastIndexOf('.')
    return if (lastDotIndex != -1) {
        String(input.toCharArray().apply { set(lastDotIndex, '$') })
    } else {
        null
    }
}