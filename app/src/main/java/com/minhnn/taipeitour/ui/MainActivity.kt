package com.minhnn.taipeitour.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.minhnn.taipeitour.ui.navigation.Routes
import com.minhnn.taipeitour.ui.screens.HomeScreen
import com.minhnn.taipeitour.ui.theme.TaipeiTourTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            TaipeiTourTheme {
                val navController = rememberNavController()
                AppNavHost(navController)
            }
        }
    }
}

@Composable
fun AppNavHost(navController: NavController) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = Routes.HOME_SCREEN
    ) {

        composable(Routes.HOME_SCREEN) {
            MainScreen(navController)
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavController) {
    Scaffold(
        content = {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                HomeScreen()
            }
        }
    )
}
