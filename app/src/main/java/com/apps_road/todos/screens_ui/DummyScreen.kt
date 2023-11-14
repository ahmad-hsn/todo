package com.apps_road.todos.screens_ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.apps_road.todos.R

@Composable
fun dummyScreen(
    modifier: Modifier,
    navHostController: NavHostController
) {
    Text(text = "Dummy Screen", modifier = modifier
        .fillMaxSize()
        .fillMaxWidth()
        .fillMaxHeight()
        .background(
            color = colorResource(
                id = R.color.white
            )
        ))
}