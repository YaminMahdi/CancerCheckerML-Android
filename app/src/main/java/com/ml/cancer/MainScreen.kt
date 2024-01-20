package com.ml.cancer

import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ml.cancer.ml.Model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder


@Composable
fun MainScreen() {
    val context = LocalContext.current
    var photo by remember { mutableStateOf<ImageBitmap?>(null) }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var predictionInfo by remember { mutableStateOf(Pair("","")) }
    val launcherCam =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
            MainScope().launch(Dispatchers.IO) {
                bitmap = it
                photo = it?.asImageBitmap()

            }
        }
    val launcherGallery =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            MainScope().launch(Dispatchers.IO) {
                photo = it.data?.data?.toBitmap(context)?.asImageBitmap()
                bitmap = it.data?.data?.toBitmap(context)
            }
        }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.app_name),
            fontSize = 30.sp,
            modifier = Modifier
                .padding(top = 20.dp)
                .padding(horizontal = 90.dp),
            fontWeight = FontWeight.Bold,
            color = Color.White,
            lineHeight = 35.sp,
            textAlign = TextAlign.Center
        )
        AnimatedVisibility(visible = photo == null) {
            Spacer(modifier = Modifier.size(150.dp))
        }
        Row {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .padding(10.dp)
                    .bounceClick()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White.copy(.08f))
                    .clickable {
                        launcherCam.launch()
                        predictionInfo = Pair("", "")
                    }
                    .padding(20.dp)

            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_camera),
                    modifier = Modifier.size(35.dp),
                    contentDescription = null,
                    tint = Color.White
                )
                Text(
                    text = "Open Camera",
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(start =10.dp),
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .padding(10.dp)
                    .bounceClick()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White.copy(.08f))
                    .clickable {
                        val intent = Intent()
                            .setType("image/*")
                            .setAction(Intent.ACTION_GET_CONTENT)
                        launcherGallery.launch(Intent.createChooser(intent, "Select a photo"))
                        predictionInfo = Pair("", "")
                    }
                    .padding(20.dp)

            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_photo),
                    modifier = Modifier.size(35.dp),
                    contentDescription = null,
                    tint = Color.White
                )
                Text(
                    text = "Open Gallery",
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(start =10.dp),
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
        photo?.let {
            Image(
                it,
                null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .padding(top = 10.dp)
                    .padding(10.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
        }
        AnimatedVisibility(visible = predictionInfo.first != "") {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(horizontal = 50.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White.copy(.08f))
                    .padding(vertical = 16.dp)
            ){
                Text(text = "Predicted Cancer Stage", color = Color.White)
                Text(
                    text = predictionInfo.first,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Yellow.copy(.8f)
                )
                Text(
                    text = "Accuracy : ${predictionInfo.second}",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        LaunchedEffect(key1 = bitmap) {
            bitmap?.let {
                val image = Bitmap.createScaledBitmap(it, 244, 244, false)

                val model = Model.newInstance(context)
                val inputFeature0 =
                    TensorBuffer.createFixedSize(intArrayOf(1, 244, 244, 3), DataType.FLOAT32)
                val byteBuffer = ByteBuffer.allocateDirect(4 * 244 * 244 * 3)
                byteBuffer.order(ByteOrder.nativeOrder())

                val intValues = IntArray(244 * 244)
                image.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)
                var pixel = 0
                for (i in 0 until 244) {
                    for (j in 0 until 244) {
                        val `val` = intValues[pixel++] // RGB
                        byteBuffer.putFloat((`val` shr 16 and 0xFF) * (1f / 1))
                        byteBuffer.putFloat((`val` shr 8 and 0xFF) * (1f / 1))
                        byteBuffer.putFloat((`val` and 0xFF) * (1f / 1))
                    }
                }
                inputFeature0.loadBuffer(byteBuffer)

                val outputs = model.process(inputFeature0)
                val outputFeature0 = outputs.outputFeature0AsTensorBuffer
                var maxPos = 0
                var maxConfidence = 0f
                Log.d("TAG", "MainScreen: ${outputFeature0.floatArray.joinToFormattedString()}")
                outputFeature0.floatArray.forEachIndexed { index, confidence ->
                    if (confidence > maxConfidence) {
                        maxConfidence = confidence
                        maxPos = index
                    }
                }
                val classes = listOf("Benign Stage","Early Stage","Pre Stage","Pro Stage")
                classes[maxPos]
                if(maxConfidence<.85)
                    predictionInfo= Pair("Unidentified", "0%")
                else
                    predictionInfo= Pair(classes[maxPos], "%.2f".format(maxConfidence*100-3)+"%")
                model.close()
            }
        }


    }

}