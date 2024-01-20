package com.ml.cancer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ml.cancer.ui.theme.CancerCheckerTheme
import com.ml.cancer.ui.theme.PurpleGrey80

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            CancerCheckerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = PurpleGrey80
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "start_screen"
                    ) {
                        composable("start_screen") {
                            StartScreen(navController)
                        }
                        composable("home"){
                            MainScreen()
                        }


                    }
                }
            }
        }
    }
}