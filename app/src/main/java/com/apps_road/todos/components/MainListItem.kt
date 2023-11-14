package com.apps_road.todos.components

import android.Manifest
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.apps_road.todos.R
import com.apps_road.todos.model.data.MainItemData
import com.apps_road.todos.screens_ui.main_item.CameraPermissionStatus
import com.apps_road.todos.screens_ui.main_item.takePhoto

@Composable
fun MainListItem(
    item: MainItemData,
    modifier: Modifier,
) {
    Card(
        border = BorderStroke(1.dp, color = colorResource(id = R.color.white)),
        shape = CardDefaults.outlinedShape,
        modifier = modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = modifier.fillMaxSize()
        ) {
            AsyncImage(
                modifier = Modifier
                    .height(50.dp)
                    .width(50.dp)
                    .clickable {},
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.imgPath)
                    .crossfade(enable = true)
                    .build(),
                contentDescription = "Avatar Image",
                contentScale = ContentScale.Crop,
            )
            Spacer(modifier = modifier.width(10.dp))
            Text(text = item.title, style = TextStyle(
                color = colorResource(id = R.color.black)
            ), modifier = modifier.padding(vertical = 10.dp))
        }
    }
}

@Preview
@Composable
fun previewListItem() {
    MainListItem(item = MainItemData("first", "sec", "path"), modifier = Modifier)
}