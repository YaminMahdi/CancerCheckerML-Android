package com.ml.cancer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ml.cancer.ui.theme.BtnColor
import com.ml.cancer.ui.theme.Pink80
import com.ml.cancer.ui.theme.Purple40
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun StartScreen(navController: NavHostController) {
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
                modifier = Modifier
                    .size(100.dp)
                    .padding(bottom = 15.dp),
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
                text = "Identify Cancer Stages of",
                fontSize = 16.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                lineHeight = 30.sp
            )
            Text(
                text = "Acute Lymphoblastic Leukemia",
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Developed by",
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.padding(top=80.dp)
            )
            Text(
                text = "Syed Tahlil Bin Abdullah",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = BtnColor),
                onClick = { navController.navigate("home") },
                modifier = Modifier.padding(70.dp)
            ) {
                Text(
                    text = "START",
                    color = Color.Black,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(4.dp)
                        .padding(horizontal = 10.dp)
                )
            }
        }
    }
}