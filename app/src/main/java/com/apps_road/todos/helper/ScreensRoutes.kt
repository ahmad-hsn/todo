package com.apps_road.todos.helper

sealed class ScreensRoutes(val route: String) {
    object CreateMainItemScreen: ScreensRoutes(Screens.MAIN_ITEM.route)
    object ItemDetailScreen: ScreensRoutes(Screens.ITEM_DETAIL.route)
    object MainListScreen: ScreensRoutes(Screens.MAIN_LIST.route)
    object DUMMY_SCREEN: ScreensRoutes(Screens.DUMMY_SCREEN.route)
}

enum class Screens(val route: String) {
    MAIN_ITEM("main_item"),
    ITEM_DETAIL("item_detail"),
    MAIN_LIST("main_list"),
    DUMMY_SCREEN("dummy_screen")
}
