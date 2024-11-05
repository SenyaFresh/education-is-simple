package com.github.educationissimple.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import kotlinx.serialization.Serializable
import kotlin.reflect.KClass


@Serializable
data object TasksGraph {

    @Serializable
    data object TasksScreen

    @Serializable
    data object CategoriesScreen

}

@Serializable
data object CalendarGraph {

    @Serializable
    data object CalendarScreen

}

@Serializable
data object AudioGraph {

    @Serializable
    data object AudioScreen

}

fun NavBackStackEntry?.routeClass(): KClass<*>? {
    return this
        ?.destination
        ?.routeClass()
}

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