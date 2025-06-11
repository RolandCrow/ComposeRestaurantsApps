package com.example.restaurant_app_presentation_pattern.viewmodel

import com.example.restaurant_app_presentation_pattern.model.Restaurant

data class RestaurantsScreenState(
    val restaurants: List<Restaurant>,
    val isLoading: Boolean,
    val error: String? = null
)
