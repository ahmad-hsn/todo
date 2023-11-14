package com.apps_road.todos.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps_road.todos.model.data.MainItemData
import com.apps_road.todos.model.repository.MainItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainItemViewModel @Inject constructor(
    var mainItemRepository: MainItemRepository
): ViewModel() {
    fun insertItem(item: MainItemData) {
        viewModelScope.launch(Dispatchers.IO) {
            val createdID = mainItemRepository.insertItem(mainItem = item)
            Log.d("createdRecordId -> ", createdID.toString())
        }
    }
}