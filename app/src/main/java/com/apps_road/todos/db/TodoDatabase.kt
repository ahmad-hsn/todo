package com.apps_road.todos.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.apps_road.todos.db.TodoDatabase.Companion.VERSION
import com.apps_road.todos.model.data.MainItemData
import com.apps_road.todos.model.service.MainItemService


@Database(entities = [MainItemData::class], version = VERSION)
abstract class TodoDatabase: RoomDatabase() {
    companion object {
        const val VERSION = 2

        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE MainItemData ADD COLUMN detail TEXT NOT NULL DEFAULT ''")
                db.execSQL("ALTER TABLE MainItemData ADD COLUMN imgPath TEXT NOT NULL DEFAULT ''")
            }
        }
    }

    abstract fun mainItemDao(): MainItemService

}