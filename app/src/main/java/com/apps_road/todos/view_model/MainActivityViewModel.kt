package com.apps_road.todos.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps_road.todos.model.data.MainItemData
import com.apps_road.todos.model.repository.MainActivityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    var mainActivityRepository: MainActivityRepository
): ViewModel() {
    var items: Flow<List<MainItemData>> = flow {
        val list = mainActivityRepository.getAll()
        emit(list)
    }
}