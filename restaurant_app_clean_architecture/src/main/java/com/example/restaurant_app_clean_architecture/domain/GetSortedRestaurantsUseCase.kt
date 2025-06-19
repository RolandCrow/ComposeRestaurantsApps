package com.example.restaurant_app_clean_architecture.domain

import com.example.restaurant_app_clean_architecture.data.RestaurantsRepository

class GetSortedRestaurantsUseCase {
    private val repository: RestaurantsRepository = RestaurantsRepository()
    suspend operator fun invoke(): List<Restaurant> {
        return repository.getRestaurants()
            .sortedBy { it.title }
    }
}