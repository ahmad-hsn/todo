package com.apps_road.todos.model.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Transaction
import com.apps_road.todos.model.data.CategoryItem

@Dao
interface ItemDetailService {
    @Insert
    suspend fun insertCategories(itemList: List<CategoryItem>)
}