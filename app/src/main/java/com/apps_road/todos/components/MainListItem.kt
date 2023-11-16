package com.apps_road.todos.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.apps_road.todos.R
import com.apps_road.todos.model.data.MainItemData

@Composable
fun MainListItem(
    item: MainItemData,
    modifier: Modifier,
    onListItemClick: () -> Unit
) {
    var itemClicked by remember {
        mutableStateOf(false)
    }
    Card(
        border = BorderStroke(1.dp, color = colorResource(id = R.color.white)),
        shape = CardDefaults.outlinedShape,
        modifier = modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
            .clickable {
                itemClicked = true
            }
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
            Text(
                text = item.title, style = TextStyle(
                    color = colorResource(id = R.color.black)
                ), modifier = modifier.padding(vertical = 10.dp)
            )
        }

        if (itemClicked) {
            LaunchedEffect(Unit) {
                onListItemClick.invoke()
            }
        }
    }
}