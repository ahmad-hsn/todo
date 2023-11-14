package com.apps_road.todos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.apps_road.todos.components.HomeFloatingActionButton
import com.apps_road.todos.components.MainListItem
import com.apps_road.todos.components.topBar
import com.apps_road.todos.helper.ScreensRoutes
import com.apps_road.todos.helper.mainNavHost
import com.apps_road.todos.model.data.MainItemData
import com.apps_road.todos.ui.theme.TodosTheme
import com.apps_road.todos.view_model.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
    val itemsList = remember { mutableStateListOf<MainItemData>() }
    val mainActivityViewModel: MainActivityViewModel = hiltViewModel()
    val modifier = Modifier

    val context = LocalContext.current

    LaunchedEffect(true) {
        CoroutineScope(Dispatchers.IO).launch {
            mainActivityViewModel.items.collect {
                itemsList.addAll(it)
            }
        }
    }

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
//        Column(
//            modifier = modifier
//                .fillMaxSize()
//                .fillMaxHeight()
//                .fillMaxWidth()
//                .padding(innerPadding),
//        ) {
//
//        }
        ConstraintLayout(
            modifier = modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .fillMaxSize()
                .padding(innerPadding)
                .background(color = colorResource(id = R.color.white))
        ) {
            val (
                floatingBtn,
                itemList
            ) = createRefs()

            LazyColumn(
                modifier = modifier
                    .constrainAs(itemList) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .fillMaxHeight()
            ) {
                items(itemsList) { item ->
                    MainListItem(item, modifier = modifier)
                }
            }

            FloatingActionButton(
                modifier = modifier
                    .height(50.dp)
                    .width(50.dp)
                    .padding(end = 10.dp, bottom = 10.dp)
                    .constrainAs(floatingBtn) {
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    },
                onClick = {
                    isClicked = true
                },
                containerColor = colorResource(id = R.color.teal_700), shape = CircleShape) {
                Icon(Icons.Filled.Add, contentDescription = null)
            }
        }

        if (isClicked) {
            mainNavHost(
                navController = navHostController,
                Modifier,
                innerPadding,
                ::onBackItemClicked
            )
        }
    }
}

@Preview
@Composable
fun showMainScreen() {
    MainView()
}

fun onBackItemClicked() {

}