package com.github.educationissimple.news.di

import kotlin.properties.Delegates.notNull

/**
 * [NewsComponent] dependencies provider.
 */
interface NewsDepsProvider {

    /**
     * [NewsComponent] dependencies.
     */
    val deps: NewsDeps

    /**
     * Singleton instance of [NewsDepsProvider] provided by [NewsDepsStore].
     */
    companion object : NewsDepsProvider by NewsDepsStore

}

/**
 * [NewsComponent] dependencies store.
 */
object NewsDepsStore : NewsDepsProvider {
    override var deps: NewsDeps by notNull()
}