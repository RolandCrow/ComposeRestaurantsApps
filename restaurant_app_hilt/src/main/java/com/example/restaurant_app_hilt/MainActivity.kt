package com.example.restaurant_app_hilt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.restaurant_app_hilt.presentation.details.RestaurantDetailsScreen
import com.example.restaurant_app_hilt.presentation.list.RestaurantsScreen
import com.example.restaurant_app_hilt.presentation.list.RestaurantsViewModel
import com.example.restaurant_app_hilt.ui.theme.ComposeRestaurantsAppsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
    NavHost(navController, startDestination = "restaurants") {
        composable(route = "restaurants") {
            val viewModel: RestaurantsViewModel = hiltViewModel()
            RestaurantsScreen(
                state = viewModel.state.value,
                onItemClick = { id->
                    navController.navigate("restaurants/$id")
                },
                onFavoriteClick = { id, oldValue ->
                    viewModel.toggleFavorite(id,oldValue)
                }
            )
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
