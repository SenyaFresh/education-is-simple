package com.github.educationissimple.glue.news.di

import com.github.educationissimple.glue.news.repositories.AdapterNewsRepository
import com.github.educationissimple.news.domain.repositories.NewsRepository
import dagger.Binds
import dagger.Module

/**
 * Module for providing dependencies related to the news repository.
 */
@Module
interface NewsRepositoryModule {

    /**
     * Binds the [AdapterNewsRepository] implementation to the [NewsRepository] interface.
     */
    @Binds
    fun bindNewsRepository(adapterNewsRepository: AdapterNewsRepository): NewsRepository

}