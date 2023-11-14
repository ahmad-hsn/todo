package com.apps_road.todos.di

import com.apps_road.todos.model.repository.MainActivityRepository
import com.apps_road.todos.model.repository.MainItemRepository
import com.apps_road.todos.model.service.MainItemService
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun  provideMainItemRespository(
        mainItemService: MainItemService
    ): MainItemRepository {
        return MainItemRepository(mainItemService)
    }

    @Provides
    @Singleton
    fun  provideMainActivityRespository(
        mainItemService: MainItemService
    ): MainActivityRepository {
        return MainActivityRepository(mainItemService)
    }
}