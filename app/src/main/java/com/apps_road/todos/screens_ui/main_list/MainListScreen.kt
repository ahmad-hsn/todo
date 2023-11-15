package com.apps_road.todos.screens_ui.main_list

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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.apps_road.todos.R
import com.apps_road.todos.components.MainListItem
import com.apps_road.todos.helper.ScreensRoutes
import com.apps_road.todos.model.data.MainItemData
import com.apps_road.todos.view_model.MainActivityViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun MainListScreen(
    modifier: Modifier,
    navHostController: NavHostController,
) {
    val itemsList = remember { mutableStateListOf<MainItemData>() }
    val mainActivityViewModel: MainActivityViewModel = hiltViewModel()

    LaunchedEffect(true) {
        CoroutineScope(Dispatchers.IO).launch {
            mainActivityViewModel.items.collect {
                itemsList.addAll(it)
            }
        }
    }

    ConstraintLayout(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .fillMaxSize()
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
                MainListItem(item, modifier = modifier) {
//                    nevigateToItemDetail = true
                }
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
                navHostController.navigate(ScreensRoutes.CreateMainItemScreen.route)
//                    isClicked = true
            },
            containerColor = colorResource(id = R.color.teal_700), shape = CircleShape
        ) {
            Icon(Icons.Filled.Add, contentDescription = null)
        }
    }
}