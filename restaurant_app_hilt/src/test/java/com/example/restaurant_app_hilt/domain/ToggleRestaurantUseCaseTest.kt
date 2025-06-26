package com.example.restaurant_app_hilt.domain

import com.example.restaurant_app_hilt.DummyContent
import com.example.restaurant_app_hilt.FakeApiService
import com.example.restaurant_app_hilt.FakeRoomDao
import com.example.restaurant_app_hilt.data.RestaurantsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class ToggleRestaurantUseCaseTest {
    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    @Test
    fun toggleRestaurant_IsUpdatingFavoriteField() = scope.runTest {
        // precondition
        val restaurantsRepository = RestaurantsRepository(
            FakeApiService(),
            FakeRoomDao(),
            dispatcher
        )
        val getSortedRestaurantsUseCase = GetSortedRestaurantsUseCase(restaurantsRepository)
        val useCase = ToggleRestaurantUseCase(
            restaurantsRepository,
            getSortedRestaurantsUseCase
        )
        restaurantsRepository.loadRestaurants()
        advanceUntilIdle()
        // action
        val restaurants = DummyContent.getDomainRestaurants()
        val targetItem = restaurants[0]
        val isFavorite = targetItem.isFavorite
        val updateRestaurants = useCase(targetItem.id, isFavorite)
        advanceUntilIdle()
        // result
        restaurants[0] = targetItem.copy(isFavorite = !isFavorite)
        assert(updateRestaurants == restaurants)
    }
}