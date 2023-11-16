package com.apps_road.todos.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.apps_road.todos.db.TodoDatabase.Companion.VERSION
import com.apps_road.todos.model.data.CategoryItem
import com.apps_road.todos.model.data.MainItemData
import com.apps_road.todos.model.service.ItemDetailService
import com.apps_road.todos.model.service.MainItemService

/***
 * exportSchema is for exporting sql schema for db so instead of writing migration by yourself build app and it will generate
 * .json file under app/schemas/DB/.json_files we need to add kapt->arguments in app level build.gradle
 **/

@Database(entities = [MainItemData::class, CategoryItem::class], version = VERSION, exportSchema = true)
abstract class TodoDatabase : RoomDatabase() {
    companion object {
        const val VERSION = 3

        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE MainItemData ADD COLUMN detail TEXT NOT NULL DEFAULT ''")
                db.execSQL("ALTER TABLE MainItemData ADD COLUMN imgPath TEXT NOT NULL DEFAULT ''")
            }
        }

        val MIGRATION_2_3: Migration = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("CREATE TABLE IF NOT EXISTS CategoryItem (`id_fk_main_item` INTEGER NOT NULL, `itemName` TEXT NOT NULL, `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, FOREIGN KEY(`id_fk_main_item`) REFERENCES `MainItemData`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
            }
        }
    }

    abstract fun mainItemDao(): MainItemService
    abstract fun itemDetailDao(): ItemDetailService

}