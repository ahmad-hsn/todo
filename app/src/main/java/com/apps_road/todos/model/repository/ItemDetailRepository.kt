package com.apps_road.todos.model.repository

import com.apps_road.todos.model.data.CategoryItem
import com.apps_road.todos.model.service.ItemDetailService

class ItemDetailRepository(
    private val itemDetailService: ItemDetailService
) {
    suspend fun insertCategories(itemList: List<CategoryItem>) = itemDetailService.insertCategories(itemList)
}