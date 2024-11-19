package com.github.educationissimple.notifications.di

import com.github.educationissimple.common.di.SingletonHolder

/**
 * Holder of [ReminderComponent] for notifications module.
 */
object ReminderComponentHolder {

    /**
     * Singleton holder for [ReminderComponent].
     */
    private object Component : SingletonHolder<ReminderComponent>({
        DaggerReminderComponent.builder()
            .deps(ReminderDepsProvider.deps)
            .build()
    })

    /**
     * Returns the instance of [ReminderComponent].
     */
    fun getInstance(): ReminderComponent {
        return Component.getInstance()
    }

}