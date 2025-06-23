package com.example.restaurant_app_hilt.presentation.list

import com.example.restaurant_app_hilt.domain.Restaurant

data class RestaurantsScreenState(
    val restaurants: List<Restaurant>,
    val isLoading: Boolean,
    val error: String? = null
)
