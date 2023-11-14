package com.apps_road.todos.model.repository

import com.apps_road.todos.model.data.MainItemData
import com.apps_road.todos.model.service.MainItemService

class MainItemRepository(
    var mainItemService: MainItemService
) {
    fun insertItem(mainItem: MainItemData) = mainItemService.insertItem(mainItem)
}