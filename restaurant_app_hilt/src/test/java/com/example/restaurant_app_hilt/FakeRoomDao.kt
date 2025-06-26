package com.example.restaurant_app_hilt

import com.example.restaurant_app_hilt.data.local.LocalRestaurant
import com.example.restaurant_app_hilt.data.local.PartialLocalRestaurant
import com.example.restaurant_app_hilt.data.local.RestaurantsDao
import kotlinx.coroutines.delay

class FakeRoomDao: RestaurantsDao {
    private var restaurants = HashMap<Int,LocalRestaurant>()
    override suspend fun getAll(): List<LocalRestaurant> {
        delay(1000)
        return restaurants.values.toList()
    }

    override suspend fun addAll(restaurants: List<LocalRestaurant>) {
        restaurants.forEach { this.restaurants[it.id] = it }
    }

    override suspend fun update(partialRestaurant: PartialLocalRestaurant) {
        delay(1000)
        updateRestaurant(partialRestaurant)
    }

    override suspend fun updateAll(partialRestaurants: List<PartialLocalRestaurant>) {
        delay(1000)
        partialRestaurants.forEach { updateRestaurant(it) }
    }

    override suspend fun getAllFavored(): List<LocalRestaurant> {
        return restaurants.values.toList().filter { it.isFavorable }
    }

    private fun updateRestaurant(
        partialRestaurant: PartialLocalRestaurant
    ) {
        val restaurant = this.restaurants[partialRestaurant.id]
        if(restaurant != null) {
            this.restaurants[partialRestaurant.id] =
                restaurant.copy(isFavorable = partialRestaurant.isFavorite)
        }

    }
}