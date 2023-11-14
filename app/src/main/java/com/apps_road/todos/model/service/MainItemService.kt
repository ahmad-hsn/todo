package com.apps_road.todos.model.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.apps_road.todos.model.data.MainItemData

@Dao
interface MainItemService {
    @Insert
    fun insertItem(itemData: MainItemData): Long

    @Query("SELECT * FROM MainItemData")
    fun getAll(): List<MainItemData>
}