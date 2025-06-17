package com.example.restaurant_app_presentation_pattern

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.restaurant_app_presentation_pattern.model.Restaurant
import com.example.restaurant_app_presentation_pattern.ui.theme.ComposeRestaurantsAppsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeRestaurantsAppsTheme {
                RestaurantsApp()
            }
        }
    }
}

@Composable
private fun RestaurantsApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "restaurants") {
        composable(route = "restaurants") {
            RestaurantsScreen { id->
                navController.navigate("restaurants/$id")
            }
        }
        composable(
            route = "restaurants/{restaurant_id}",
            arguments = listOf(navArgument("restaurant_id") {
                type = NavType.IntType
            })
        ) {
            RestaurantDetailsScreen()
        }
    }
}