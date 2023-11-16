package com.apps_road.todos.helper

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.apps_road.todos.screens_ui.main_item.CreateMainItemScreen
import com.apps_road.todos.screens_ui.item_detail.ItemDetailScreen
import com.apps_road.todos.screens_ui.main_list.MainListScreen

enum class ClickedItemType {
    MAIN_ITEM_SCREEN,
    MAIN_LIST_SCREEN,
}

@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier,
    iPadding: PaddingValues?,
    onItemClicked: (ClickedItemType, Any?, NavHostController?) -> Unit?
) {
    NavHost(
        navController = navController,
        startDestination = ScreensRoutes.MainListScreen.route,
        modifier = if (iPadding != null) modifier.padding(iPadding) else modifier
    ) {
        composable(ScreensRoutes.CreateMainItemScreen.route) {
            CreateMainItemScreen(modifier, navController, onItemClicked)
        }
        composable(ScreensRoutes.ItemDetailScreen.route) {
            ItemDetailScreen(modifier, navController)
        }
        composable(ScreensRoutes.MainListScreen.route) {
            MainListScreen(modifier, navController, onItemClicked)
        }
    }
}