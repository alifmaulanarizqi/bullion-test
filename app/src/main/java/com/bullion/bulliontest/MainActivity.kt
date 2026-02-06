package com.bullion.bulliontest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.rememberNavController
import com.bullion.bulliontest.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // set icon color in status bar dark color
        WindowInsetsControllerCompat(window, window.decorView)
            .isAppearanceLightStatusBars = true

        setContent {
            AppTheme {
                val navController = rememberNavController()
                Navigation(
                    navHostController = navController,
                )
            }
        }
    }
}