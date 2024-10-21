package com.github.educationissimple.tasks.domain.entities


data class Task(
    val id: Long = 0,
    val text: String,
    val isCompleted: Boolean = false,
    val categoryId: Long? = null,
    val priority: Priority = Priority.NoPriority,
    val date: String? = null
) {

    sealed class Priority(val value: Int) {

        data object TopPriority : Priority(2)

        data object SecondaryPriority : Priority(1)

        data object NoPriority : Priority(0)

        companion object {
            fun fromValue(value: Int): Priority {
                return when (value) {
                    2 -> TopPriority
                    1 -> SecondaryPriority
                    else -> NoPriority
                }
            }
        }

    }
}