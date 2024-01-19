package com.ml.cancer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun SplashScreen(navController: NavHostController) {
    LaunchedEffect(key1 = null){
        delay(2.seconds)
        navController.navigate("home"){
            popUpTo("splash_screen"){
                inclusive = true
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                painter = painterResource(id = R.drawable.logo),
                modifier = Modifier.size(100.dp).padding(bottom = 15.dp),
                contentDescription = null
            )
            Text(
                text = stringResource(R.string.app_name),
                fontSize = 30.sp,
                modifier = Modifier.padding(bottom = 100.dp),
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "Identify Acute Lymphoblastic Leukemia",
                fontSize = 22.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                lineHeight = 30.sp
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 200.dp)
            ){
                Text(text = "Developed by-", fontSize = 12.sp, color = Color.White)
                Text(
                    text = "Syed Tahlil Bin Abdullah",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}