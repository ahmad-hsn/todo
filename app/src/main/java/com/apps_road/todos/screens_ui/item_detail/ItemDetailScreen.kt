package com.apps_road.todos.screens_ui.item_detail

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.apps_road.todos.view_model.ItemDetailViewModel

@Composable
fun ItemDetailScreen(
    modifier: Modifier,
    navHostController: NavHostController,
) {
    val itemDetailViewModel: ItemDetailViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        itemDetailViewModel.insertCategories(1)
    }
    Text(text = "asdlfkjnaisoduf a iasd fiajdfijdufiasd ")
}