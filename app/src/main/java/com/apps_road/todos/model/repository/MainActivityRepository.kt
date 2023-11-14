package com.apps_road.todos.model.repository

import com.apps_road.todos.model.service.MainItemService

class MainActivityRepository(
    val mainItemService: MainItemService
) {
    fun getAll() = mainItemService.getAll()
}