package com.apps_road.todos.di

import android.content.Context
import androidx.room.Room
import com.apps_road.todos.db.TodoDatabase
import com.apps_road.todos.db.TodoDatabase.Companion.MIGRATION_1_2
import com.apps_road.todos.model.service.MainItemService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule(
) {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): TodoDatabase {
        return Room.databaseBuilder(context, TodoDatabase::class.java, TodoDatabase::class.java.name)
            .addMigrations(MIGRATION_1_2)
            .build()
    }

    @Provides
    @Singleton
    fun provideMainItemService(
        appDatabase: TodoDatabase
    ): MainItemService {
        return appDatabase.mainItemDao()
    }
}