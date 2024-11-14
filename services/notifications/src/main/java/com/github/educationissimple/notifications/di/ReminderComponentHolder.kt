package com.github.educationissimple.notifications.di

import com.github.educationissimple.common.di.SingletonHolder

object ReminderComponentHolder {

    private object Component : SingletonHolder<ReminderComponent>({
        DaggerReminderComponent.builder()
            .deps(ReminderDepsProvider.deps)
            .build()
    })

    fun getInstance(): ReminderComponent {
        return Component.getInstance()
    }

}