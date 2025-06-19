package com.example.restaurant_app_clean_architecture.presentation.list

import com.example.restaurant_app_clean_architecture.domain.Restaurant

data class RestaurantsScreenState(
    val restaurants: List<Restaurant>,
    val isLoading: Boolean,
    val error: String? = null
)
