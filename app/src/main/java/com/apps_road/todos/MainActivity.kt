package com.apps_road.todos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.apps_road.todos.components.topBar
import com.apps_road.todos.helper.ClickedItemType
import com.apps_road.todos.helper.MainNavHost
import com.apps_road.todos.helper.ScreensRoutes
import com.apps_road.todos.ui.theme.TodosTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodosTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainView()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val navHostController = rememberNavController()
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val topBarState = rememberSaveable { (mutableStateOf(true)) }
    var isClicked by remember { mutableStateOf(false) }
    val nevigateToItemDetail by remember { mutableStateOf(false) }

    val modifier = Modifier

    when (navBackStackEntry?.destination?.route) {
        ScreensRoutes.CreateMainItemScreen.route -> {
            topBarState.value = true //make it false to hide topBar in that screen
        }
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AnimatedVisibility(
                visible = topBarState.value
            ) {
                topBar(title = "Todo's", modifier = modifier, scrollBehavior) {
                    val popped = navHostController.popBackStack()
                    if (!popped) {
                        isClicked = false
                    }
                }
            }
        }
    ) { innerPadding ->
        MainNavHost(
            navController = navHostController,
            Modifier,
            innerPadding,
            ::onItemClicked
        )

//        if(nevigateToItemDetail) {
//            navHostController.navigate(ScreensRoutes.ItemDetailScreen.route)
//        }
    }
}

fun onItemClicked(clickType: ClickedItemType, item: Any?, navHostController: NavHostController?) {
    when(clickType) {
        ClickedItemType.MAIN_ITEM_SCREEN -> {

        }

        ClickedItemType.MAIN_LIST_SCREEN -> {
            navHostController?.navigate(ScreensRoutes.ItemDetailScreen.route)
        }

        else -> {}
    }
}