package com.apps_road.todos.helper

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.apps_road.todos.screens_ui.main_item.CreateMainItemScreen
import com.apps_road.todos.screens_ui.dummyScreen
import com.apps_road.todos.screens_ui.main_item.CameraDemo

@Composable
fun mainNavHost(
    navController: NavHostController,
    modifier: Modifier,
    iPadding: PaddingValues?,
    onItemClicked: () -> Unit?
) {
    NavHost(
        navController = navController,
        startDestination = ScreensRoutes.CreateMainItemScreen.route,
        modifier = if (iPadding != null) modifier.padding(iPadding) else modifier
    ) {
        composable(ScreensRoutes.CreateMainItemScreen.route) {
            CreateMainItemScreen(modifier, navController, onItemClicked)
        }
        composable(ScreensRoutes.DUMMY_SCREEN.route) {
            dummyScreen(modifier, navController)
        }
    }
}