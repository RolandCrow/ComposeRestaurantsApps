package com.example.restaurant_app_clean_architecture

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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.restaurant_app_clean_architecture.presentation.details.RestaurantsDetailsScreen
import com.example.restaurant_app_clean_architecture.presentation.list.RestaurantsScreen
import com.example.restaurant_app_clean_architecture.presentation.list.RestaurantsViewModel
import com.example.restaurant_app_clean_architecture.ui.theme.ComposeRestaurantsAppsTheme

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
            val viewModel: RestaurantsViewModel = viewModel()
            RestaurantsScreen(
                state = viewModel.state.value,
                onItemClick = { id ->
                    navController.navigate("restaurants/$id")
                },
                onFavoriteClick = { id, oldValue ->
                    viewModel.toggleFavorite(id,oldValue)
                }
            )
        }
        composable(
            route = "restaurants/{restaurant_id}",
            arguments = listOf(navArgument("restaurant_id"){
                type = NavType.IntType
            })
        ) {
            RestaurantsDetailsScreen()
        }
    }
}