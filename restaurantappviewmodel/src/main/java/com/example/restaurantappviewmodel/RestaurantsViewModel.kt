package com.example.restaurantappviewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class RestaurantsViewModel(
    private val stateHandle: SavedStateHandle
): ViewModel() {
    val state = mutableStateOf(dummyRestaurants.restoreSelection())

    fun toggleFavorite(id: Int) {
        val restaurants = state.value.toMutableStateList()
        val itemIndex = restaurants.indexOfFirst { it.id == id }
        val item = restaurants[itemIndex]
        restaurants[itemIndex] = item.copy(isFavorite = !item.isFavorite)
        storeSelection(restaurants[itemIndex])
        state.value = restaurants
    }

    private fun storeSelection(item: Restaurant) {
        val savedToggled = stateHandle.get<List<Int>?>(FAVORITES)
            .orEmpty().toMutableList()
        if (item.isFavorite) savedToggled.add(item.id)
        else savedToggled.remove(item.id)
        stateHandle[FAVORITES] = savedToggled
    }

    private fun List<Restaurant>.restoreSelection(): List<Restaurant> {
        stateHandle.get<List<Int>?>(FAVORITES)?.let { selectedId ->
            val restaurantsMap = this.associateBy{it.id}
            selectedId.forEach { id->
                restaurantsMap[id]?.isFavorite = true
            }
            return restaurantsMap.values.toList()
        }
        return this

    }

    companion object {
        const val FAVORITES = "favorites"
    }
}