package com.apps_road.todos.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.apps_road.todos.R

@Composable
fun HomeFloatingActionButton(
    modifier: Modifier,
    icon: ImageVector,
    onClick: () -> Unit
) {
//    FloatingActionButton(
//        modifier = modifier
//            .height(50.dp)
//            .width(50.dp)
//            .align(Alignment.BottomEnd),
//        onClick = onClick,
//        containerColor = colorResource(id = R.color.teal_700), shape = CircleShape) {
//        Icon(icon, contentDescription = null)
//    }
}