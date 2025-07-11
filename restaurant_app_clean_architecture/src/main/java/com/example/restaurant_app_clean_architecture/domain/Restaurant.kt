package com.example.restaurant_app_clean_architecture.domain

data class Restaurant(
    val id: Int,
    val title:String,
    val description: String,
    val isFavorite: Boolean = false
)
