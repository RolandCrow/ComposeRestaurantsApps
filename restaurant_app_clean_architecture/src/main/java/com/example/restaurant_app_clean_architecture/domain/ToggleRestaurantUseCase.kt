package com.example.restaurant_app_clean_architecture.domain

import com.example.restaurant_app_clean_architecture.data.RestaurantsRepository

class ToggleRestaurantUseCase {
    private val repository: RestaurantsRepository = RestaurantsRepository()
    private val getSortedRestaurantsUseCase = GetSortedRestaurantsUseCase()
    suspend operator fun invoke(id: Int, oldValue: Boolean): List<Restaurant> {
        val newFav = oldValue.not()
        repository.toggleFavoriteRestaurant(id,newFav)
        return getSortedRestaurantsUseCase()
    }
}