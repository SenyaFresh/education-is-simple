package com.github.educationissimple.news.di

import kotlin.properties.Delegates.notNull

interface NewsDepsProvider {

    val deps: NewsDeps

    companion object : NewsDepsProvider by NewsDepsStore

}

object NewsDepsStore : NewsDepsProvider {
    override var deps: NewsDeps by notNull()
}