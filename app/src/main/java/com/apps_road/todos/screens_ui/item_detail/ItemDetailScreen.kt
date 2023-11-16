package com.apps_road.todos.screens_ui.item_detail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.apps_road.todos.model.data.MainItemData
import com.apps_road.todos.view_model.ItemDetailViewModel

@Composable
fun ItemDetailScreen(
    modifier: Modifier,
    navHostController: NavHostController,
    item: MainItemData?
) {
    val itemDetailViewModel: ItemDetailViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        itemDetailViewModel.insertCategories(1)
    }
    Text(text = "asdlfkjnaisoduf a iasd fiajdfijdufiasd \n ${item?.title} ")
}