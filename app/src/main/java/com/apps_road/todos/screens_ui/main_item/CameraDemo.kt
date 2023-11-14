package com.apps_road.todos.screens_ui.main_item

import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.apps_road.todos.ui.theme.TodosTheme
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.min
import androidx.compose.material.MaterialTheme
import androidx.navigation.NavHostController

enum class CameraPermissionStatus { NoPermission, PermissionGranted, PermissionDenied }

@Composable
fun CameraDemo(
    modifier: Modifier,
    navHostController: NavHostController,
    onItemClicked: () -> Unit?
) {

    val cameraPermissionStatusState = remember {
        mutableStateOf(CameraPermissionStatus.NoPermission)
    }
    val photoUriState: MutableState<Uri?> = remember {
        mutableStateOf(null)
    }
    val hasPhotoState = remember {
        mutableStateOf(value = false)
    }
    val resolver = LocalContext.current.contentResolver
    val cameraPermissionStatus by remember { cameraPermissionStatusState }
    val hasPhoto by remember { hasPhotoState }
    var shouldShowFullImage by remember { mutableStateOf(false) }

    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()){ isGranted: Boolean ->
            if (isGranted) {
                cameraPermissionStatusState.value = CameraPermissionStatus.PermissionGranted
            } else {
                cameraPermissionStatusState.value = CameraPermissionStatus.PermissionDenied
            }
        }

    val takePhotoLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { isSaved ->
            hasPhotoState.value = isSaved
        }

    val takePhoto: () -> Unit = {
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

    // Ideally these would be cached instead of reloaded
    val getThumbnail: (Uri?) -> ImageBitmap? = { uri ->
        val targetSize = 256f
        println("URI is $uri")
        uri?.let {
            println("Opening Input Stream")
            resolver.openInputStream(it)
        }?.let {
            BitmapFactory.decodeStream(it)
        }?.let {
            val height = it.height.toFloat()
            val width = it.width.toFloat()
            val scaleFactor = min(targetSize / height, targetSize / width)
            Bitmap.createScaledBitmap(it, (scaleFactor * width).toInt() , (scaleFactor * height).toInt(), true)
        }?.let {
            val rotation = getImageRotation(resolver, uri)
            Bitmap.createBitmap(it, 0, 0, it.width, it.height, Matrix().apply { postRotate(rotation.toFloat()) }, true)
        }?.asImageBitmap()

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

    TodosTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TakePhotoButton(
                    cameraPermissionStatus = cameraPermissionStatus,
                    requestPermissionLauncher = requestPermissionLauncher,
                    takePhoto = takePhoto
                )
                if (hasPhoto) {
                    val bitmap = getThumbnail(photoUriState.value)
                    println("Is bitmap null: $bitmap")
                    if (bitmap != null) {
                        Image(
                            bitmap = bitmap,
                            contentDescription = "Thumbnail of Save Photo",
                            modifier = Modifier.clickable {
                                shouldShowFullImage = true
                            }
                        )
                    }
                }
            }

            if (shouldShowFullImage && hasPhoto) {
                val bitmap =getFullImage(photoUriState.value)
                if (bitmap != null){
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            shouldShowFullImage = false
                        }){
                        Image(
                            bitmap = bitmap,
                            contentDescription = "Full image of Save Photo",
                            modifier = Modifier.align(Alignment.Center)
                        )
                        Surface(
                            modifier = Modifier
                                .background(MaterialTheme.colors.background)
                                .align(Alignment.Center)
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "Click to Close",
                                style = MaterialTheme.typography.h4.copy(
                                    fontWeight = FontWeight.ExtraBold
                                )
                            )
                        }
                    }


                } else {
                    shouldShowFullImage = false
                }

            }
        }
    }
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

@Composable
fun TakePhotoButton(
    cameraPermissionStatus: CameraPermissionStatus,
    requestPermissionLauncher: ActivityResultLauncher<String>,
    takePhoto: () -> Unit
) {
    OutlinedButton(
        onClick = {
            when (cameraPermissionStatus) {
                CameraPermissionStatus.NoPermission ->
                    requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)

                CameraPermissionStatus.PermissionGranted ->
                    takePhoto()


                CameraPermissionStatus.PermissionDenied -> {}

            }
        }
    ) {
        when (cameraPermissionStatus) {
            CameraPermissionStatus.NoPermission ->
                Text(text = "Request Camera Permissions")

            CameraPermissionStatus.PermissionDenied ->
                Text(text = "Camera Permissions Have Been Denied")

            CameraPermissionStatus.PermissionGranted ->
                Text(text = "Take Photo")
        }
    }
}