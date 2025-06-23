package com.example.restaurant_app_hilt.domain

import com.example.restaurant_app_hilt.data.RestaurantsRepository
import javax.inject.Inject

class GetSortedRestaurantsUseCase @Inject constructor(
    private val repository: RestaurantsRepository
) {
    suspend operator fun invoke() : List<Restaurant> {
        return repository.getRestaurants().sortedBy {
            it.title
        }
    }
}