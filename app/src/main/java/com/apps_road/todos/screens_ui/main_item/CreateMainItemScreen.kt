package com.apps_road.todos.screens_ui.main_item

import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.apps_road.todos.R
import com.apps_road.todos.R.drawable.ic_image_placeholder
import com.apps_road.todos.helper.ClickedItemType
import com.apps_road.todos.model.data.MainItemData
import com.apps_road.todos.view_model.MainItemViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

enum class CameraPermissionStatus { NoPermission, PermissionGranted, PermissionDenied }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateMainItemScreen(
    modifier: Modifier,
    navHostController: NavHostController,
    onItemClicked: (ClickedItemType, Any?, NavHostController) -> Unit?
) {
    val mainItemViewModel: MainItemViewModel = hiltViewModel()
    val resolver = LocalContext.current.contentResolver

    var itemTitle by remember {
        mutableStateOf("")
    }

    var itemDetail by remember {
        mutableStateOf("")
    }

    var titleError by remember {
        mutableStateOf(false)
    }

    val cameraPermissionStatusState = remember {
        mutableStateOf(CameraPermissionStatus.NoPermission)
    }

    val photoUriState: MutableState<Uri?> = remember {
        mutableStateOf(null)
    }

    val hasPhotoState = remember {
        mutableStateOf(false)
    }

    val cameraPermissionStatus by remember {
        cameraPermissionStatusState
    }

    val hasPhoto by remember {
        hasPhotoState
    }

    var isClicked by remember {
        mutableStateOf(false)
    }

    var imageUri: Any? by remember {
        mutableStateOf(ic_image_placeholder)
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            cameraPermissionStatusState.value = CameraPermissionStatus.PermissionGranted
        } else {
            cameraPermissionStatusState.value = CameraPermissionStatus.PermissionDenied
        }
    }

    val takePhotoLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSaved ->
        hasPhotoState.value = isSaved
    }

    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) {
        if (it != null) {
            Log.d("PhotoPicker", "Selected URI: $it")
            imageUri = it
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }


    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(color = colorResource(id = R.color.white))
            .fillMaxSize()
    ) {
        OutlinedTextField(
            value = itemTitle,
            onValueChange = {
                itemTitle = it
            },
            maxLines = 1, modifier = modifier.fillMaxWidth(),
            label = {
                Text(text = "Enter Title")
            },
            isError = titleError,
            supportingText = {
                if (titleError) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Title should not empty",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            trailingIcon = {
                if (titleError)
                    Icon(Icons.Filled.Warning, "error", tint = MaterialTheme.colorScheme.error)
            },
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(value = itemDetail, onValueChange = {
            itemDetail = it
        }, maxLines = 10, modifier = modifier.fillMaxWidth(), label = {
            Text(text = "Enter Detail")
        }, isError = itemTitle.isEmpty())
        Spacer(modifier = Modifier.height(20.dp))
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clickable {
                    isClicked = true
//                    photoPicker.launch(
//                        PickVisualMediaRequest(
//                            ActivityResultContracts.PickVisualMedia.ImageOnly
//                        )
//                    )
                    when (cameraPermissionStatus) {
                        CameraPermissionStatus.NoPermission ->
                            requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)

                        CameraPermissionStatus.PermissionGranted ->
                            takePhoto(
                                hasPhotoState, resolver, takePhotoLauncher, photoUriState
                            )

                        CameraPermissionStatus.PermissionDenied -> {}

                    }
                },
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUri)
                .crossfade(enable = true)
                .build(),
            contentDescription = "Avatar Image",
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.height(20.dp))
        ElevatedButton(
            onClick = {
                if (itemTitle.isEmpty()) {
                    titleError = true
                } else {
                    mainItemViewModel.insertItem(
                        MainItemData(
                            itemTitle,
                            itemDetail,
                            imageUri?.toString() ?: ""
                        )
                    )
                    val isBackStacked: Boolean = navHostController.popBackStack()
                    if (!isBackStacked) {
                        onItemClicked.invoke(ClickedItemType.MAIN_ITEM_SCREEN,null, navHostController)
                    }
                }
            },
            modifier = modifier
                .fillMaxWidth(),
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.teal_700))
        ) {
            Text(text = "Create")
        }

        val getFullImage: (Uri?) -> ImageBitmap? = { uri ->
            uri?.let {
                resolver.openInputStream(it)
            }?.let {
                BitmapFactory.decodeStream(it)
            }?.let {
                val rotation = getImageRotation(resolver, uri)
                Bitmap.createBitmap(it, 0, 0, it.width, it.height, Matrix().apply { postRotate(rotation.toFloat()) }, true)
            }?.asImageBitmap()
        }

        if (hasPhoto) {
            val bitmap = getFullImage(photoUriState.value)
            println("Is bitmap null: $bitmap")
            if (bitmap != null) {
                imageUri = photoUriState.value
            }
        }
    }
}

fun takePhoto(
    hasPhotoState: MutableState<Boolean>,
    resolver: ContentResolver,
    takePhotoLauncher: ManagedActivityResultLauncher<Uri, Boolean>,
    photoUriState: MutableState<Uri?>
) {
    hasPhotoState.value = false

    val values = ContentValues().apply {
        val title = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        put(MediaStore.Images.Media.TITLE, "Compose Camera Example Image - $title")
        put(MediaStore.Images.Media.DISPLAY_NAME, title)
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
    }

    val uri = resolver.insert(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        values
    )

    takePhotoLauncher.launch(uri)
    photoUriState.value = uri
}

private fun getImageRotation(resolver: ContentResolver, uri: Uri): Int {
    val cursor = resolver.query(uri, arrayOf(MediaStore.Images.Media.ORIENTATION), null, null, null)
    var result = 0

    cursor?.apply {
        moveToFirst()
        val index = getColumnIndex(MediaStore.Images.Media.ORIENTATION)
        result = getInt(index)
        close()
    }
    println("Rotation = $result")
    return result
}