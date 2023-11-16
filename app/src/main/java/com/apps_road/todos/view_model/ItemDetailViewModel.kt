package com.apps_road.todos.view_model

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.apps_road.todos.model.data.CategoryItem
import com.apps_road.todos.model.repository.ItemDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ItemDetailViewModel @Inject constructor(
    private val itemDetailRepository: ItemDetailRepository
): ViewModel() {
    var counter = mutableStateOf(1)

    suspend fun insertCategories(mainListItemId: Long) {
        val item1 = CategoryItem(mainListItemId, "firstCategory")
        val item2 = CategoryItem(mainListItemId, "secCategory")

        val list = arrayListOf<CategoryItem>()
        list.add(item1)
        list.add(item2)
//        itemDetailRepository.insertCategories(list)
    }
}