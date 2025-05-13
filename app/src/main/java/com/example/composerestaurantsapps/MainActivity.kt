package com.example.composerestaurantsapps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.composerestaurantsapps.ui.theme.ComposeRestaurantsAppsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeRestaurantsAppsTheme {
               RestaurantScreen()
                }
            }
        }
    }