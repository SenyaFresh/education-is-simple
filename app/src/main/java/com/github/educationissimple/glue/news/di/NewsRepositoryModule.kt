package com.github.educationissimple.glue.news.di

import com.github.educationissimple.glue.news.repositories.AdapterNewsRepository
import com.github.educationissimple.news.domain.repositories.NewsRepository
import dagger.Binds
import dagger.Module

@Module
interface NewsRepositoryModule {

    @Binds
    fun bindNewsRepository(adapterNewsRepository: AdapterNewsRepository): NewsRepository

}