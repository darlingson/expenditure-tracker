package com.codeshinobi.expendituretracker.screens

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Parcelable
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.codeshinobi.expendituretracker.screens.components.DotLoadingAnimation
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.android.parcel.Parcelize

@Composable
fun GalleryScreen(
    navController: NavController
) {
    var scannedText by remember { mutableStateOf("") }
    var isProcessing by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var recogText: RecognizeResult? = null
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState(), enabled = true),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Gallery")
        RequestContentPermission(
            navController = navController,
            onTextScanned = { text ->
                scannedText = text
                recogText = RecognizeResult(text)
            },
            onProcessing = { processing ->
//                Toast.makeText(context, "Text scanning completed", Toast.LENGTH_SHORT).show()
                isProcessing = false
                val gson: Gson = GsonBuilder().create()
                if (recogText == null || recogText?.text.isNullOrEmpty()){
//                    Toast.makeText(context, "No text found", Toast.LENGTH_SHORT).show()
                }
                else {
                    Log.d("TAG", "recogText: ${recogText?.text}")
                    val recogJson = gson.toJson(recogText)
                    navController.navigate(
                        "text_recon/{text}"
                            .replace(
                                oldValue = "{text}",
                                newValue = recogJson
                            )
                    )
                }
            }
        )
        if (isProcessing) {
            Text(text = isProcessing.toString())
            DotLoadingAnimation()
        }
        else {
            Text(text = isProcessing.toString())
            Text(text = scannedText)
        }
    }
}

@Composable
fun RequestContentPermission(
    navController: NavController,
    onTextScanned: (String) -> Unit,
    onProcessing: (Boolean) -> Unit
    ) {
    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    /**
     * this code is from the article : https://ngengesenior.medium.com/pick-image-from-gallery-in-jetpack-compose-5fa0d0a8ddaf
     * */
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current
    val bitmap = remember {
        mutableStateOf<Bitmap?>(null)
    }

    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    Column {
        Button(onClick = {
            launcher.launch("image/*")
        }) {
            Text(text = "Pick image")
        }

        Spacer(modifier = Modifier.height(12.dp))

        imageUri?.let {
            try {
                bitmap.value = if (Build.VERSION.SDK_INT < 28) {
                    MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                } else {
                    val source = ImageDecoder.createSource(context.contentResolver, it)
                    ImageDecoder.decodeBitmap(source)
                }
            } catch (e: Exception) {
                Log.e("GalleryScreen", "Image loading failed: ${e.message}", e)
            }
            onProcessing(true)
            bitmap.value?.let { btm ->
                Image(
                    bitmap = btm.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.size(400.dp)
                )

                // Create InputImage from Bitmap
                val inputImage = InputImage.fromBitmap(btm, 0)
                recognizer.process(inputImage)
                    .addOnSuccessListener { visionText ->
                        Log.d("TAG", "onSuccess: ${visionText.text}")
                        onTextScanned(visionText.text)
                        onProcessing(false)
                    }
                    .addOnFailureListener { e ->
                        Log.d("TAG", "onFailure: ${e.message}", e)
                        onProcessing(false)
                    }
            }
        }
    }
}

@Parcelize
data class ScanResultsData(val text: String) : Parcelable
data class RecognizeResult(val text: String)