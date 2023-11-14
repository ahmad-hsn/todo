package com.apps_road.todos.helper

sealed class ScreensRoutes(val route: String) {
    object CreateMainItemScreen: ScreensRoutes(Screens.MAIN_ITEM.route)
    object DUMMY_SCREEN: ScreensRoutes(Screens.DUMMY_SCREEN.route)
}

enum class Screens(val route: String) {
    MAIN_ITEM("main_item"),
    DUMMY_SCREEN("dummy_screen")
}
